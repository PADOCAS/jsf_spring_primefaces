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