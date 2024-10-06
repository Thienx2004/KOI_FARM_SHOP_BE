package com.group2.KoiFarmShop;

import com.google.cloud.storage.Bucket;
import com.google.firebase.cloud.StorageClient;
import com.group2.KoiFarmShop.dto.response.KoiFishDetailReponse;
import com.group2.KoiFarmShop.exception.AppException;
import com.group2.KoiFarmShop.exception.ErrorCode;
import com.group2.KoiFarmShop.service.AccountServiceImp;
import com.group2.KoiFarmShop.service.FirebaseService;
import com.group2.KoiFarmShop.service.KoiFishService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.URL;
import com.google.cloud.storage.Blob;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@SpringBootTest(classes = KoiFarmShopApplication.class)
class KoiFarmShopApplicationTests {
	@Autowired
	private AccountServiceImp accountServiceImp;
	@Autowired
	private KoiFishService koiFishService;
	@Mock
	private Bucket bucket;

	@Mock
	private Blob blob;
	@Test
	void testGenerateOTPFormat() {
		// Gọi phương thức generateOTP
		String otp = accountServiceImp.generateOTP();

		// Kiểm tra độ dài của OTP là 6
		assertEquals("Kiểm tra độ dài của OTP là 6",6, otp.length());

		// Kiểm tra OTP chỉ chứa các chữ số
		assertTrue(otp.matches("\\d+"), "OTP should only contain digits");
	}

	@Test
	void testGenerateOTPRange() {
		// Gọi phương thức generateOTP
		String otp = accountServiceImp.generateOTP();

		// Chuyển OTP từ chuỗi sang số nguyên
		int otpValue = Integer.parseInt(otp);

		// Kiểm tra OTP nằm trong phạm vi từ 100000 đến 999999
		assertTrue(otpValue >= 100000 && otpValue <= 999999, "OTP should be between 100000 and 999999");
	}

	@Test
	void testGetKoiFishByIdInvalidIdThrowsException() {
		// Kiểm tra xem ngoại lệ AppException có được ném ra khi id <= 0
		int invalidId = -1;

		AppException exception = assertThrows(AppException.class, () -> {
			koiFishService.getKoiFishById(invalidId);
		});

		// Kiểm tra xem thông báo lỗi của exception có đúng với ErrorCode.INVALIDNUMBER
		assertEquals("tìm cá koi với id không hợp lệ",ErrorCode.INVALIDNUMBER, exception.getErrorCode());
	}
	
}
