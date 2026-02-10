docker run -d \
  --name mongo-letsplay \
  -p 27017:27017 \
  -v mongo_data:/data/db \
  mongo:6
