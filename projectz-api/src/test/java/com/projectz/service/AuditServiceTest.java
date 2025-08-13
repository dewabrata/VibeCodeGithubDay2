package com.projectz.service;

import com.projectz.entity.LogProduk;
import com.projectz.repository.LogProdukRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuditServiceTest {

    @Mock
    private LogProdukRepository logProdukRepository;

    @InjectMocks
    private AuditService auditService;

    @Test
    void getProductAudits_WithFilters_ShouldCallRepositoryWithCorrectParameters() {
        // Arrange
        Long productId = 1L;
        String flag = "C";
        LocalDateTime dateFrom = LocalDateTime.now().minusDays(1);
        LocalDateTime dateTo = LocalDateTime.now();
        Pageable pageable = PageRequest.of(0, 10);
        
        LogProduk logProduk = new LogProduk();
        logProduk.setId(1L);
        logProduk.setIdProduk(productId);
        logProduk.setFlag(flag);
        
        Page<LogProduk> expectedPage = new PageImpl<>(Arrays.asList(logProduk));
        
        when(logProdukRepository.findByFilters(productId, flag, dateFrom, dateTo, pageable))
            .thenReturn(expectedPage);

        // Act
        Page<LogProduk> result = auditService.getProductAudits(productId, flag, dateFrom, dateTo, pageable);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertEquals(productId, result.getContent().get(0).getIdProduk());
        assertEquals(flag, result.getContent().get(0).getFlag());
        
        verify(logProdukRepository, times(1))
            .findByFilters(productId, flag, dateFrom, dateTo, pageable);
    }

    @Test
    void getProductAudits_WithNullFilters_ShouldWork() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10);
        Page<LogProduk> expectedPage = new PageImpl<>(Arrays.asList());
        
        when(logProdukRepository.findByFilters(null, null, null, null, pageable))
            .thenReturn(expectedPage);

        // Act
        Page<LogProduk> result = auditService.getProductAudits(null, null, null, null, pageable);

        // Assert
        assertNotNull(result);
        assertTrue(result.getContent().isEmpty());
        
        verify(logProdukRepository, times(1))
            .findByFilters(null, null, null, null, pageable);
    }
}