package com.example.stock.service;

import com.example.stock.domain.Stock;
import com.example.stock.repository.StockRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StockService {

    private StockRepository stockRepository;

    public StockService(final StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

//    @Transactional
//    public void decrease(Long id, Long quantity) {
//        Stock stock = stockRepository.findById(id)
//                .orElseThrow();
//
//        stock.decrease(quantity);
//
//        stockRepository.saveAndFlush(stock);
//    }

    /**
     * synchronized는 각 프로세스안에서만 보장
     * 서버가 여러대일때 레이스 컨디션 그대로 발생
     */
    public synchronized void decrease(Long id, Long quantity) {
        Stock stock = stockRepository.findById(id)
                .orElseThrow();

        stock.decrease(quantity);

        stockRepository.saveAndFlush(stock);
    }
}
