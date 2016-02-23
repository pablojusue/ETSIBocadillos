# -*- coding: utf-8 -*-
#!/usr/bin/env python
import bluetooth
import sys

target_name="My host"
target_address=None
nearby_devices=None

class IdentificadorMoviles(object):
	
	def __init__(self, nombre):
		self.nombre = nombre

	def ObtenerMiDireccion(self):
		try:
			nearby_devices=bluetooth.discover_devices() 
			
		except Exception:
			print "Error al escanear"
			raise
		for bdaddr in nearby_devices:
			if self.nombre==bluetooth.lookup_name(bdaddr):
				target_address=bdaddr
		
moviles=IdentificadorMoviles(target_name)
moviles.ObtenerMiDireccion()	 