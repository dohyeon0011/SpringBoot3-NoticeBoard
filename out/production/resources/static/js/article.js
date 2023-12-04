// 삭제 기능
const deleteButton = document.getElementById('delete-btn');

if (deleteButton) {
    deleteButton.addEventListener('click', event =>  {
      let id = document.getElementById('article-id').value;
      fetch(`/api/articles/${id}`, {    // fetch 메소드를 통해 /api/articles/delete 요청을 보냄
        method: 'DELETE'
      })
          .then(() => {  // fetch 메소드가 실행이 잘 되었을 때
            alert('삭제가 완료되었습니다.');
            location.replace(`/articles`);
          });
    });
}

// 수정 기능
// id가 modify-btn인 엘리먼트 조회
const modifyButton = document.getElementById('modify-btn');

if (modifyButton) {
    modifyButton.addEventListener('click', event => {
        let params = new URLSearchParams(location.search);
        let id = params.get('id');

        fetch(`/api/articles/${id}`, {
            method: 'PUT',
            headers: {
                "Content-Type": "application/json",     // 입력한 데이터를 JSON 형태로 변환
            },
            body: JSON.stringify({
                title: document.getElementById('title').value,
                content: document.getElementById('content').value
            })
        })
            .then(() => {
                alert('수정이 완료되었습니다.');
                location.replace(`/articles/${id}`);
            });
    });
}

// 등록 기능
const createButton = document.getElementById("create-btn");

if (createButton) {
    createButton.addEventListener("click", (event) => {
        fetch("/api/articles", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                title: document.getElementById("title").value,
                content: document.getElementById("content").value,
            }),
        }).then(() => {
            alert('등록이 완료되었습니다.')
            location.replace("/articles");
        });
    });
}