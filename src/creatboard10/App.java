package creatboard10;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import creatboard10.dto.Article;
import creatboard10.dto.Member;
import creatboard10.util.Util;

public class App {
	private List<Article> articles;
	private List<Member> members;
	
	// 작성한 대로 공간을 증가하기 위해 ArraytList를 사용해랴한다
	// makeTestDate()와 main 메서드를 둘다 조종하기 위해서는 두개의 메서드가 묶여있는 main class 안에 있어야 한다
	// 또한 ArrayList의 타입이 public static인 이유는 상위 개념이 static 이면 그 안의 하위 개념 역시 static
	// 이어야 하기 때문이다
	App(){
		articles = new ArrayList<>();
		members = new ArrayList<>();
	}

	public void run() {
		System.out.println("==프로그램 시작==");

		// 스캐너 타입의 sc라는 변수를 만들고 스캐너객체를 만들어서 연결한다
		Scanner sc = new Scanner(System.in);

		makeTestDate();

		int lastArticleId = 3;
		// while문 밖에 있는 이유는 매번 0이 되면 안되기때문이다

		while (true) {
			// 무한루프
			System.out.println("명령어) ");
			String cmd = sc.nextLine();
			System.out.println("입력된명령어 > " + cmd);

			// 이 안에 있는 명령어가 계속 실행된다

			if (cmd.equals("exit")) {
				// 조건문 cmd라는변수의 값이 exit라는 문자와 같다면
				break;
				// 탈출해라 break를 하지 않으면 무한반복문으로 실행된다
			}
			if (cmd.equals("member join")) {

				int id = members.size()+ 1;
				

				String regDate = Util.getNowDateStr();
				
				String loginId; 
				String loginPw;
				String loginPwChk;
				
				while(true) {
					System.out.println("아이디 : ");
					loginId = sc.nextLine();
					
					if(isJoinableLoginId(loginId) == false) {
						System.out.printf("%s는 이미 사용중인 아이디 입니다\n",loginId);
						continue;
					}
					
					break;
				}


				
				
				while(true) {
					
					System.out.println("비밀번호 : ");
					loginPw = sc.nextLine();
					System.out.println("비밀번호 확인: ");
					loginPwChk = sc.nextLine();
					
					if(!(loginPw.equals(loginPwChk))) {
						System.out.println("비밀번호를 다시 입력해주세요");
						continue;
					}
					break;
				}
				System.out.println("이름 : ");
				String name = sc.nextLine();
				
				Member member = new Member(id, regDate, loginId, loginPw, name);
				members.add(member);

				System.out.printf("%d번 회원이 생성되었습니다\n", id);

			}
			else if (cmd.equals("article write")) {

				int id = lastArticleId + 1;
				lastArticleId = id;

				String regDate = Util.getNowDateStr();

				System.out.println(regDate);

				System.out.println("제목) ");
				String title = sc.nextLine();
				System.out.println("내용) ");
				String body = sc.nextLine();

				Article article = new Article(id, regDate, title, body);
				// 제목과 내용을 쓰기 위해 article write 기능의 조건문에 있어야 한다
				articles.add(article);
				// ArrayList의 요소를 추가 하기 위함이디

				System.out.printf("%d번글이 생성되었습니다\n", id);

			}

			else if (cmd.startsWith("article list")) {
				if (articles.size() == 0) {

					System.out.println("게시글이 없습니다");
					continue;
				} else {
					System.out.println("게시물이 있습니다");
				}
				
				String searchKeyword = cmd.substring("article list".length()).trim();
				
				List<Article> forPrintArticles = articles;
				
				if(searchKeyword.length()>0) {
					//searchkeyword가 존재 항때
					forPrintArticles = new ArrayList<>();
					//forPrintArticles 에 ArrayList라는 객체를 만들어 넣겠다
					for(Article article : articles) {
						//처음부터 끝까지 돌린다
						if(article.title.contains(searchKeyword)) {
							//contains는 문자열이 들어있는지 유무 확인 하는것인데
							//searchKeyword가 title이라는 변수에 문자열이 있으면 이라는 조건문
							forPrintArticles.add(article);
							//forPrintfArticles라는 변수에 article을 추가한다
						}
					}
					
					if(forPrintArticles.size() ==0) {
						System.out.println("검색결과가 없습니다");
					}
				}
				
				
				System.out.println("번호	|	제목	|	조회");
				for (int i = forPrintArticles.size() - 1; i >= 0; i--) {
					// 게시물이 저장되고 보여줄때 1번 부터 보여주게 하기 위함
					Article article = forPrintArticles.get(i);

					System.out.printf("%d	|	%s	|	%d\n", article.id, article.title, article.hit);
				}

			} else if (cmd.startsWith("article detail ")) {
				// 앞에있는 cmd가 뒤에있는 문자열로 시작할경우

				String[] cmdBit = cmd.split(" ");
				int id = Integer.parseInt(cmdBit[2]);

				Article foundArticle = getArticleById(id);

				if (foundArticle == null) {
					// 게시물이 없는 경우
					System.out.printf("%d번 게시물이 존재하지 않습니다\n", id);
					continue;
				}

				foundArticle.increaseHit();
				// 증가 하는 것이 게시물이 만들어 질때 증가 해야 하므로 여기에 있어야 한다

//				System.out.printf("%d번 게시물은 존재 합니다\n",id);
				System.out.printf("번호 : %d\n", foundArticle.id);
				System.out.printf("날짜 : %s\n", foundArticle.regDate);
				System.out.printf("제목 : %s\n", foundArticle.title);
				System.out.printf("내용 : %s\n", foundArticle.body);
				System.out.printf("조회 : %d\n", foundArticle.hit);

			}

			else if (cmd.startsWith("article modify ")) {
				// 앞에있는 cmd가 뒤에있는 문자열로 시작할경우

				String[] cmdBit = cmd.split(" ");
				// split은 안에있는 인자를 기준으로 나눈다
				int id = Integer.parseInt(cmdBit[2]);
			
				Article foundArticle = getArticleById(id);


				
				if (foundArticle == null) {
					// 게시물이 없는 경우
					System.out.printf("%d번 게시물이 존재하지 않습니다\n", id);
					continue;
				}
				System.out.println("수정할 제목) ");
				String title = sc.nextLine();
				System.out.println("수정할 내용) ");
				String body = sc.nextLine();

				foundArticle.title = title;
				foundArticle.title = body;

				System.out.printf("게시물이 수정되었습니다\n", id);

			} else if (cmd.startsWith("article delete ")) {
				// 앞에있는 cmd가 뒤에있는 문자열로 시작할경우

				String[] cmdBit = cmd.split(" ");
				int id = Integer.parseInt(cmdBit[2]);

				Article foundArticle = getArticleById(id);

			
				if (foundArticle == null) {
					// 게시물이 없는 경우
					System.out.printf("%d번 게시물이 존재하지 않습니다\n", id);
					continue;
				}

				articles.remove(foundArticle);

				System.out.printf("%d번 게시물이 삭제 되었습니다\n", id);

			}

			else {
				System.out.println("존재하디 않는 명령어 입니다");
			}
		}

		sc.close(); // 스캐너기능을 하용하면 반드시 이 실행문을 추가햐야한다

		System.out.println("==프로그램 종료==");

	}

	private int getMemberIndexId(String loginId) {
		int i =0;
		
		for (Member member : members) {
			//향상된 for문으로 사용 하여 처음부터 끝까지 확인 가능하다

			if (member.loginId.equals(loginId)) {
				return i;
			}
			i++;
		}
		return -1;
		
	}
	private boolean isJoinableLoginId(String loginId) {
		
		int index =getMemberIndexId(loginId);
		
		if(index != -1) {
			return false;
			
		}
		return true;
	}


	private int getMemberIndexById(int id) {
		int i =0;
		
		for (Article article : articles) {
			//향상된 for문으로 사용 하여 처음부터 끝까지 확인 가능하다

			if (article.id == id) {
				return i;
			}
			i++;
		}
		return -1;
	}
	
	private Article getArticleById(int id) {
		
		int index = getMemberIndexById(id);
		
		if(index != -1) {
			return articles.get(index);
			}
		
		return null;
	}

	private void makeTestDate() {

		System.out.println("테스트 데이터를 생성합니다");
		articles.add(new Article(1, Util.getNowDateStr(), "테스트1", "테스트1", 10));
		articles.add(new Article(2, Util.getNowDateStr(), "테스트2", "테스트2", 20));
		articles.add(new Article(3, Util.getNowDateStr(), "테스트3", "테스트3", 30));
	}


}
