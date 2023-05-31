//Função logout - template principal:
function logout(contextPath) {
    //Executar essa ação com um post usando spring para invalidar sessão do usuário ao fazer logout:
    var urlPost = 'invalidar_sessao';

    $.ajax({
        type: 'POST',
        url: urlPost
    }).always(function resposta() {
        //Executar isso sempre depois que a função ajax post for executada!!! 
        //Chamando o Spring Security para encerrar a sessão de controle do usuário e voltar para tela de login:
        document.location = contextPath + '/j_spring_security_logout';
    });
}

//Função logout - seta para sair -> sair do sistema vai abrir um menu:
function abrirMenupop() {
    $("#menupop").show("slow").mouseleave(function() {
        fecharMenupop();
    });
}

//Função logout - seta para sair (fechar menu):
function fecharMenupop() {
    if($("#menupop").is(":visible")) {
        //Fechar menu com efeito slow:
        $("#menupop").hide("slow");
    }
}

//Invalidar Sessão - redirecionar para outra página:
function invalidarSession(context, pagina) {
    document.location = (context + pagina + ".jsf");
}

//Validar Senha Login:
function validarSenhaLogin() {
    //Caso retornar true, vai deixar enviar o formulário
    //Caso retornar false, não deixa enviar o formulário
    //SpringSecurity precisa receber os parametros dos campos assim para validar corretamente (j_username e j_password)
    j_username = document.getElementById("j_username").value;
    j_password = document.getElementById("j_password").value;

    if (j_username === null
            || j_username.trim() === '') {
        alert("Informe o Login.");
        //JQuery vai dar o Foco para o usuário de login ser digitado
        $('#j_username').focus();
        return false;
    }

    if (j_password === null
            || j_password.trim() === '') {
        alert("Informe a Senha.");
        //JQuery vai dar o Foco para o usuário de login ser digitado
        $('#j_password').focus();
        return false;
    }

    return true;
}