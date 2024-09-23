start cmd /k"cd Servidor GRPC/tp-sistemas-distribuidos-grpc && mvn spring-boot:run"
start cmd /k"cd Cliente GRPC/src && python app.py"
start cmd /k"cd Vista && python -m http.server 8000"