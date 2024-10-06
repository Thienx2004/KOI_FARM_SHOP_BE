package com.group2.KoiFarmShop;

import com.group2.KoiFarmShop.dto.response.KoiFishDetailReponse;
import com.group2.KoiFarmShop.service.KoiFishService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@SpringBootTest(classes = KoiFarmShopApplication.class)
class KoiFarmShopApplicationTests {

	@Autowired
	private KoiFishService koiFishService;

	@Autowired
	private KoiFishService koiFishServiceImpl;


	@Test
	void testCompareKoiFish() {
		// Mock koi fish responses
		KoiFishDetailReponse koiFish1 = KoiFishDetailReponse.builder()
				.id(1)
				.origin("Japan")
				.gender(true)
				.age(2)
				.size(45.5)
				.personality("Friendly")
				.price(2000.0)
				.build();

		KoiFishDetailReponse koiFish2 = KoiFishDetailReponse.builder()
				.id(2)
				.origin("China")
				.gender(false)
				.age(3)
				.size(50.0)
				.personality("Aggressive")
				.price(2500.0)
				.build();

		// Khi gọi phương thức getKoiFishById(), trả về koiFish1 và koiFish2
		when(koiFishService.getKoiFishById(1)).thenReturn(koiFish1);
		when(koiFishService.getKoiFishById(2)).thenReturn(koiFish2);

		// Thực thi phương thức compareKoiFish
		List<KoiFishDetailReponse> result = koiFishServiceImpl.compareKoiFish(1, 2);

		// Kiểm tra số lượng koi fish trả về
		assertEquals("Số lượng Koi trả về",2, result.size());

		// Kiểm tra từng koi fish có đúng như mock không
		assertEquals("so sánh koi 1",koiFish1, result.get(0));
		assertEquals("so sánh koi 2",koiFish2, result.get(1));
	}

}
