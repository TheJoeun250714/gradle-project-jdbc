package com.my.servlet;


public class User {
    // int 대신 Long Integer 사용하는 이유
    // int는 null 이 0으로 자동 치환
    // index는 0번 부터 생성
    // 실수로 null 고객을 0번째에 존재하는 고객으로 인식할 수 있다. ( 잘못된 인식가능)
    // 객체 형태인 Long Integer 는 null 을 사용할 수 있으며, null 일 경우 잘못된 정보 조회입니다. 와 같은 예외 상황 전달
    private Long id;
    private String name;
    private String email;

    // NoArgsConstructors
    public User() {
    }

    // AllArgsConstructors
    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    // getter / setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
