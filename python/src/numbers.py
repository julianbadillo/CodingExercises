for i in range(0, 1000):
	s = "%03d"%i
	# 1 4 7 tiene un digito bien pero en el lugar incorrecto
	if s[0]=='1' or s[1] == '4' or s[2] == '7' or s.count('1') + s.count('4') + s.count('7') != 1:
		continue
	# 1 8 9 tiene un digito en el lugar correto
	if (s[0] != '1' and s[1] != '8' and s[2] != '9') or s.count('1') + s.count('8') + s.count('9') != 1:
		continue
	# 9 6 4 tiene dos digitos bien pero en el lugar incorrecto
	if s[0]=='9' or s[1] == '6' or s[2] == '4' or s.count('9') + s.count('6') + s.count('4') != 2:
		continue
	# 5 2 3 todos los digitos estan incorrectos
	if '5' in s or '2' in s or '3' in s:
		continue
	# 2 8 6 tiene un digito bien pero en el lugar incorrecto
	if s[0]=='2' or s[1] == '8' or s[2] == '6' or s.count('2') + s.count('8') + s.count('6') != 1:
		continue
	print(s)

print("#  = ", c)
	