#!/bin/bash
echo "🛠️ Docker 및 Docker Compose 설치 확인..."

if ! command -v docker &> /dev/null; then
    echo "🚨 Docker가 설치되지 않았습니다. 설치를 진행합니다..."
    sudo apt update
    sudo apt install -y docker.io
    sudo systemctl start docker
    sudo systemctl enable docker
else
    echo "✅ Docker가 이미 설치되어 있습니다."
fi

if ! command -v docker-compose &> /dev/null; then
    echo "🚨 Docker Compose가 설치되지 않았습니다. 설치를 진행합니다..."
    sudo curl -L "https://github.com/docker/compose/releases/latest/download/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
    sudo chmod +x /usr/local/bin/docker-compose
else
    echo "✅ Docker Compose가 이미 설치되어 있습니다."
fi

echo "🚀 배포 시작: $(date)"

# 프로젝트 디렉토리 이동
cd /home/ubuntu || exit

echo "🔎 .env 파일 확인 중..."
if [ ! -f "/home/ubuntu/.env" ]; then
    echo "❌ .env 파일이 없습니다! GitHub Actions에서 제대로 생성되었는지 확인하세요."
    exit 1
fi

# .env 파일 로드
export "$(grep -v '^#' /home/ubuntu/.env | xargs)"

echo "🛑 기존 컨테이너 중지 중..."
docker-compose down  # 기존 컨테이너 중지 및 제거

echo "🐳 최신 Docker 이미지 가져오는 중..."
docker pull tiemo0708/thinkboo-backend-app:latest # 최신 이미지 가져오기

echo "🔒 파일 권한 설정 중..."
chmod 600 /home/ubuntu/.env

echo "🔄 컨테이너 재시작 중..."
docker-compose up -d --force-recreate --remove-orphans  # 최신 이미지로 컨테이너 재시작

echo "🧹 사용하지 않는 Docker 이미지 정리"
docker image prune -f  # 불필요한 이미지 삭제

echo "✅ 배포 완료!"