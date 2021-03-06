package com.dbsys.rs.payment.controller;

import java.sql.Date;
import java.util.List;

import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dbsys.rs.lib.ApplicationException;
import com.dbsys.rs.lib.EntityRestMessage;
import com.dbsys.rs.lib.ListEntityRestMessage;
import com.dbsys.rs.lib.entity.Pembayaran;
import com.dbsys.rs.payment.service.PembayaranService;

@Controller
@RequestMapping("/pembayaran")
public class PembayaranController {
	
	@Autowired
	private PembayaranService pembayaranService;
	
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public EntityRestMessage<Pembayaran> simpan(@RequestBody Pembayaran pembayaran) throws ApplicationContextException, PersistenceException {
		pembayaran = pembayaranService.simpan(pembayaran);
		return EntityRestMessage.createPembayaran(pembayaran);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{kode}")
	@ResponseBody
	public EntityRestMessage<Pembayaran> get(@PathVariable String kode) throws ApplicationContextException, PersistenceException {
		Pembayaran pembayaran = pembayaranService.get(kode);
		return EntityRestMessage.createPembayaran(pembayaran);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/pasien/{pasien}")
	@ResponseBody
	public ListEntityRestMessage<Pembayaran> getByPasien(@PathVariable Long pasien) throws ApplicationContextException, PersistenceException {
		List<Pembayaran> list = pembayaranService.get(pasien);
		return ListEntityRestMessage.createListPembayaran(list);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{awal}/to/{akhir}")
	@ResponseBody
	public ListEntityRestMessage<Pembayaran> get(@PathVariable Date awal, @PathVariable Date akhir) throws ApplicationException, PersistenceException {
		List<Pembayaran> list = pembayaranService.get(awal, akhir);
		return ListEntityRestMessage.createListPembayaran(list);
	}
}
