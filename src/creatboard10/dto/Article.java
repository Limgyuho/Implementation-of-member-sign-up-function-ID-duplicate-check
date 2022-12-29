package creatboard10.dto;

import creatboard10.dto.Dto;

public class Article extends Dto{

	public String title;
	public String body;
	public int hit; // 증가시키기 위해 변수를 만들고

	public Article(int id, String regDate, String title, String body) {
		this(id, body, body, body, 0);
		// 다른 생성자에게 일을 떠넘기는 방식이다
	}

	public Article(int id, String regDate, String title, String body, int hit) {
		this.id = id;
		this.title = title;
		this.body = body;
		this.regDate = regDate;
		this.hit = hit; // 초기 값을 0으로 두고 //
	}

	public void increaseHit() {
		this.hit++; // 메서드 함수에 증감식을 넣으면 위에서 실행 될때마다 1씩 증가한다
	}

}
