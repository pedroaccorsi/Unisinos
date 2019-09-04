.data
	gv_prompt_start_message:    .asciiz "Press 1 to start the program "
	gv_prompt_empty_line:       .asciiz "\n"
	gv_prompt_input_a:          .asciiz "Write the value of A: "
	gv_prompt_input_b:          .asciiz "Write the value of B: "
	gv_prompt_output_a:         .asciiz "A = "
	gv_prompt_output_b:         .asciiz "B = "
	gv_prompt_output_a_minus_b: .asciiz "A - B = "
	gv_prompt_output_a_gray:    .asciiz "A Gray: "
	gv_prompt_output_b_gray:    .asciiz "B Gray: "
	gv_prompt_output_counter:   .asciiz "Counter: "
	gv_prompt_space:            .asciiz " "
		
.macro write_string(%iv_string)
	la   $a0, %iv_string
	li   $v0, 4
	syscall
.end_macro

.macro read_integer(%rv_integer)
	li $v0, 5                         	 	   
	syscall                           	  	   
	move %rv_integer, $v0
.end_macro

.macro write_integer(%iv_integer)
	move $a0, %iv_integer
	li   $v0, 1
	syscall
.end_macro

.macro count_to_5( )
	addi $t5, $t5, 1
	write_string( gv_prompt_output_counter )
	write_integer($t5)
	write_string( gv_prompt_empty_line )
	bne $t5, 5, iterate_on_counter_5
.end_macro

.macro count_to_7( )
	addi $t5, $t5, 1
	write_string( gv_prompt_output_counter )
	write_integer($t5)
	write_string( gv_prompt_empty_line )
	bne $t5, 7, iterate_on_counter_7
.end_macro

.macro write_a_and_b( %iv_a, %iv_b)
    	write_string( gv_prompt_output_a )                 
	write_integer(%iv_a)
	write_string( gv_prompt_empty_line )
	
  	write_string( gv_prompt_output_b )                
	write_integer(%iv_b)
	write_string( gv_prompt_empty_line )
.end_macro

.macro write_a_and_b_gray( %iv_a, %iv_b)
    	write_string( gv_prompt_output_a_gray )                 
	write_integer(%iv_a)
	write_string( gv_prompt_empty_line )
	
  	write_string( gv_prompt_output_b_gray )                
	write_integer(%iv_b)
	write_string( gv_prompt_empty_line )
.end_macro

.text

	# this event will call itself untill the user writes the value '1', which is the start command.
	at_selection_screen:
		and  $s1, $0, $0 		          	   
		addi $s1, $0, 1  		          	   
  
  		# read an integer from user input and save it into $t0
		write_string( gv_prompt_start_message )           
		read_integer($t0)                                 
		
		# if $t0 != $s1, go to at_selection_screen event
		bne $t0, $s1, at_selection_screen 	  	  
	
	#once start_of_selection is reached, the user has already written '1', so we can initialize all the important variables
	start_of_selection:
		and $s1, $0, $0                   	  	  
		and $s2, $0, $0                   	  	     
		and $s3, $0, $0                   	  	  
		and $s4, $0, $0                   	  	  
		and $t6, $0, $0                   	 	   
		and $t7, $0, $0                   	 	 
		
		# read an integer from user input and save it into $s1, this will be value A
	    	write_string( gv_prompt_input_a )        	  
		read_integer($s1)
		
		# read an integer from user input and save it into $s2, this will be value B
	    	write_string( gv_prompt_input_b )         	   
		read_integer($s2)

		# write A and B
		write_string( gv_prompt_empty_line )
	    	write_a_and_b( $s1, $s2)
		write_string( gv_prompt_empty_line )
		
		# initialize $t5 in zero
		and $t5, $0, $0
			
	# this event will call itself untill it finishes to count to 5
	iterate_on_counter_5: count_to_5( )
		
	# once we get here, it means that the counter has finished and we can begin the comparrison
	begin_a_and_b_comparisson:
		slt $t1, $s2, $s1                      # if B < A 
	 	beq $t1, $0, event_a_minus_b           # false, A <=B, then goto A - B
	 	beq $t1, $1, event_conv_to_gray        #if here, than convert to gray 


	event_conv_to_gray:	
		# convert A to gray					
		srl  $t0, $s1, 1 	               
		xor  $v1, $t0, $s1                     
		add  $s3, $0,  $v1
		
		#convert B to gray		    
		srl  $t0, $s2, 1	               
		xor  $v0, $t0, $s2
		add  $s4, $0, $v0
		
		#print A and B converted to gray
		write_string( gv_prompt_empty_line )
		write_a_and_b_gray( $s3, $s4)
		write_string( gv_prompt_empty_line )
		
		#if we got here, it means we dont need to execute a_minus_b event, so jump straight to finish_comparisson
		j finish_a_and_b_comparisson
		
	event_a_minus_b:
		# $s3 = %s1 - $s2
		sub $s3, $s1, $s2
		
		# write the result from a - b
		write_string( gv_prompt_empty_line )
		write_string( gv_prompt_output_a_minus_b )
		write_integer( $s3 )  
		write_string( gv_prompt_empty_line )

	finish_a_and_b_comparisson:
		slt $t1, $s2, $s1                      # if B < A 
		beq $t1, $0, end_of_selection          # false, A <= B, then goto end_of_selection
		write_string( gv_prompt_empty_line )  
		and $t5, $0, $0
		iterate_on_counter_7: count_to_7( ) 
		write_string( gv_prompt_empty_line )
	 	j at_selection_screen
	 	
	end_of_selection: 
		
