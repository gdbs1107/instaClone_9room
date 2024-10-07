<h1>인스타그램 클론 프로젝트</h1>


<p>이 프로젝트는 구름톤에서 진행한 스터디의 일환으로, 인스타그램의 주요 기능을 클론 코딩한 프로젝트입니다. 해당 프로젝트는 최신 백엔드 기술과 클라우드 서비스를 활용하여 인스타그램의 주요 기능을 구현하는 것을 목표로 했습니다.</p>



<img width="509" alt="image" src="https://github.com/user-attachments/assets/a03c3749-150a-40dc-9ed0-c9b7fea58957">




<h2>주요 기능</h2>
<ul>
<li><strong>유저 인증</strong>: Spring Security,JWT를 이용하여 Refresh,Access Token 기능.</li>
<li><strong>피드 & 릴스</strong>: 유저가 이미지와 영상을 업로드하고, 좋아요 및 댓글을 달 수 있는 기능.</li>
<li><strong>스토리</strong>: 인스타그램의 스토리 기능 구현.</li>
<li><strong>팔로우 & 팔로워</strong>: 유저 간 팔로우, 팔로워 관계를 맺고 관리할 수 있는 기능.</li>
<li><strong>댓글 & 좋아요</strong>: 게시물, 댓글, 스토리, 릴스에 대한 좋아요와 댓글 기능.</li>
<li><strong>채팅</strong>: Spring WebSocket, STOMP 를 이용한 유저 간 실시간 채팅 기능 구현.</li>
</ul>



<h2>기술 스택</h2>
<ul>
<li><strong>Backend</strong>: Java Spring, Spring Security, Spring WebSocket ...</li>
<li><strong>Database</strong>: MySQL (AWS RDS)</li>
<li><strong>Cloud</strong>: AWS (EC2, ELB, RDS, S3)</li>
<li><strong>CI/CD</strong>: Git, GitHub, GitHub Actions</li>
<li><strong>Other</strong>: Docker</li>
</ul>

<h2>ERD</h2>
<p>다음은 해당 프로젝트의 ERD(Entity Relationship Diagram)입니다:</p>
<img width="976" alt="스크린샷 2024-10-07 오전 9 24 36" src="https://github.com/user-attachments/assets/25f40147-d6da-49a4-8097-682ded279f96">


<h3>배포 환경</h3>
<ul>
<li><strong>AWS EC2</strong>: 애플리케이션 서버 호스팅</li>
<li><strong>AWS RDS</strong>: MySQL 데이터베이스 호스팅</li>
<li><strong>AWS S3</strong>: 이미지 및 영상 파일 저장소</li>
<li><strong>GitHub Actions</strong>: CI/CD 파이프라인 설정</li>
</ul>