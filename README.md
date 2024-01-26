# Pre-purchase


### wsl2/ubuntu/docker download
- wsl --install : : 리눅스용 윈도우 하위 시스템 다운로드
- wsl -d Ubuntu-22.04 : 로컬에 있는 Ubuntu 터미널 접속
- lsb_release -a : Ubuntu 버전 확인

#### docker engine gpg 키 등록
- sudo apt-get update
- sudo apt-get install ca-certificates curl gnupg
- sudo install -m 0755 -d /etc/apt/keyrings
- curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo gpg --dearmor -o /etc/apt/keyrings/docker.gpg
- sudo chmod a+r /etc/apt/keyrings/docker.gpg

#### apt source 에 docker 관련 추가
- echo \
  "deb [arch="$(dpkg --print-architecture)" signed-by=/etc/apt/keyrings/docker.gpg] https://download.docker.com/linux/ubuntu \
  "$(. /etc/os-release && echo "$VERSION_CODENAME")" stable" | \
  sudo tee /etc/apt/sources.list.d/docker.list > /dev/null
- sudo apt-get update

#### docker engine 설치
- sudo apt-get install -y docker-ce docker-ce-cli containerd.io \
- docker-buildx-plugin docker-compose-plugin docker-compose


### docker-container connect
- docker pull mysql:[mysql_version]
- docker run --name [container_name] -e MYSQL_ROOT_PASSWORD=[password] -d -p 3306:3306 mysql:[latest]
- docker start [container_name] : mysql 실행
- docker exec -it [container_name] bash : bash 실행
- mysql -u root -p : 컨테이너 안에 mysql에 root 사용자로 접속
