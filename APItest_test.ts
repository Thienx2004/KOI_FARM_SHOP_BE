Feature('APItest');

Scenario('Đăng nhập với tài khoản không tồn tại', async ({ I }) => {
    const loginRequest = {
        email: 'thiennhse182297@email.com',
        password: '123456@'
    };

    const response = await I.sendPostRequest('/login/signin', loginRequest);

    // Kiểm tra mã trạng thái
    // Kiểm tra nội dung phản hồi
    I.seeResponseContainsJson({
        statusCode: 5002,
        message: "Không tìm thấy account"
    });

});
Scenario('Đăng nhập với tài khoản google', async ({ I }) => {
    const loginRequest = {
        email: 'thiennhse182297@fpt.edu.vn',
        name: 'Nguyễn Hoàng Thiên'
    };

    const response = await I.sendPostRequest('/login/signingoogle', loginRequest);

    // Kiểm tra mã trạng thái
    // Kiểm tra nội dung phản hồi
    I.seeResponseContainsJson({
        statusCode: 200,
        message: "Đăng nhập thành công"
    });

});
Scenario('Đăng nhập với thông tin không hợp lệ', async ({ I }) => {
    const loginRequest = {
        email: 'thiennhse182297@fpt.edu.vn',
        password: '123456@'
    };

    const response = await I.sendPostRequest('/login/signin', loginRequest);

    // Kiểm tra mã trạng thái
    // Kiểm tra nội dung phản hồi
    I.seeResponseContainsJson({
        statusCode: 5001,
        message: "Sai mật khẩu"
    });

});
