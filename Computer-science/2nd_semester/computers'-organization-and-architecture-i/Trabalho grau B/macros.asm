.macro print_string(%str)
	.text
		la   $a0, %str
		li   $v0, 4
		syscall
.end_macro
