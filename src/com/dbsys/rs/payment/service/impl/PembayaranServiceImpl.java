package com.dbsys.rs.payment.service.impl;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dbsys.rs.lib.entity.Pembayaran;
import com.dbsys.rs.payment.repository.PembayaranRepository;
import com.dbsys.rs.payment.service.PembayaranService;

@Service
@Transactional(readOnly = true)
public class PembayaranServiceImpl implements PembayaranService {

	@Autowired
	private PembayaranRepository pembayaranRepository;
	
	@Override
	@Transactional(readOnly = false)
	public Pembayaran simpan(Pembayaran pembayaran) {
		
		/*
		 * Cicilan pasien otomatis ditambah sesuai jumlah pembayaran, 
		 * karena Pasien cascade dengan Pembayaran (CascadeType.MERGE).
		 * 
		 * Jika cicilan sudah melebihi total tagihan, 
		 * secara otomatis mengubah status pasien menjadi StatusPasien.LUNAS.
		 */
		return pembayaranRepository.save(pembayaran);
	}

	@Override
	public Pembayaran get(String kode) {
		return pembayaranRepository.findOne(kode);
	}

	@Override
	public List<Pembayaran> get(Long pasien) {
		return pembayaranRepository.findByPasien_Id(pasien);
	}

	@Override
	public List<Pembayaran> get(Date awal, Date akhir) {
		return pembayaranRepository.findByTanggalBetween(awal, akhir);
	}
}
