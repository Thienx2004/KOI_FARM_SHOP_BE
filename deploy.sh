echo "Building app..."
./mvnw clean package -DskipTests

echo "Deploy files to server..."
# Sao chép be.jar và .env đến máy chủ
scp -r target/be.jar root@14.225.218.236:/var/www/be/
scp -r .env root@14.225.218.236:/var/www/be/

ssh root@14.225.218.236 <<EOF
pid=\$(sudo lsof -t -i:8080)

if [ -z "\$pid" ]; then
    echo "Start server..."
else
    echo "Restart server..."
    sudo kill -9 "\$pid"
fi
cd /var/www/be
# Chạy ứng dụng Java với file .env
#export \$(cat .env | xargs) # Nạp biến từ .env
# Nạp biến từ .env và in giá trị của từng biến
while IFS='=' read -r key value; do
    # Bỏ qua dòng trống hoặc dòng bắt đầu bằng dấu '#'
    if [[ -z "\$key" || "\$key" == \#* ]]; then
        continue
    fi
    # Bỏ qua dòng không có dấu '='
    if [[ "\$key" != *[=]* ]]; then
        continue
    fi
    export "\$key=\$value"
    echo "\$key=\$value"
done < .env
java -jar be.jar
EOF
exit
echo "Done!"
