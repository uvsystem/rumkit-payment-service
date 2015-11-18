package com.dbsys.rs.payment.service.impl;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dbsys.rs.lib.entity.Pasien;
import com.dbsys.rs.lib.entity.Pembayaran;
import com.dbsys.rs.lib.entity.Tagihan;
import com.dbsys.rs.lib.entity.Tagihan.StatusTagihan;
import com.dbsys.rs.payment.repository.PasienRepository;
import com.dbsys.rs.payment.repository.PembayaranRepository;
import com.dbsys.rs.payment.service.PembayaranService;

@Service
@Transactional(readOnly = true)
public class PembayaranServiceImpl implements PembayaranService {

	@Autowired
	private PembayaranRepository pembayaranRepository;
	@Autowired
	private PasienRepository pasienRepository;
	
	@Override
	@Transactional(readOnly = false)
	public Pembayaran simpan(Pembayaran pembayaran) {
		
		/*
		 * Update property pembayaran & statusTagihan pada tagihan,
		 * agar diidentifikasi sudah sibayar.
		 */
		for (Tagihan tagihan : pembayaran.getListTagihan()) {
			tagihan.setPembayaran(pembayaran);
			tagihan.setStatus(StatusTagihan.LUNAS);
		}
		
		pembayaran = pembayaranRepository.save(pembayaran);

		/*
		 * Cicilan pasien otomatis ditambah sesuai jumlah pembayaran, 
		 * karena Pasien cascade dengan Pembayaran (CascadeType.MERGE).
		 */
		pembayaran.getPasien().addCicilan(pembayaran.getJumlah());
		Pasien pasien = pembayaran.getPasien();
		pasienRepository.updateCicilan(pasien.getId(), pasien.getCicilan());

		return pembayaran;
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
