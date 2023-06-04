var arrayIdsElementsPage = new Array;

/**
 * Carrega um array global com os ids de todos os componentes da pagina Para ter
 * faciliades em obtencao de valores dos componentes bem como trabalhar com ajax
 */
function carregarIdElementosPagina() {
    arrayIdsElementsPage = new Array;
    for (form = 0; form <= document.forms.length; form++) {
        var formAtual = document.forms[form];
        if (formAtual !== undefined) {
            for (i = 0; i < document.forms[form].elements.length; i++) {
                if (document.forms[form].elements[i].id !== '') {
                    arrayIdsElementsPage[i] = document.forms[form].elements[i].id;
                }
            }
        }
    }
}

/**
 * Retorno o valor do id do componente dentro do documento html passando como
 * parametro a do id declarada em jsf
 * 
 * @param id
 */
function getValorElementPorId(id) {
    carregarIdElementosPagina();
    for (i = 0; i < arrayIdsElementsPage.length; i++) {
        var valor = "" + arrayIdsElementsPage[i];
        if (valor.indexOf(id) > -1) {
            return valor;
        }
    }
    return undefined;
}

/**
 * Transforma nossos objetos calendar em português
 * 
 * @returns {undefined}
 */
function localeData_pt_br() {
    PrimeFaces.locales['pt'] = {
        closeText: 'Fechar',
        prevText: 'Anterior',
        nextText: 'Próximo',
        currentText: 'Começo',
        monthNames: ['Janeiro', 'Fevereiro', 'Marcio', 'Abril', 'Maio',
            'Junho', 'Julho', 'Agosto', 'Setembro', 'Outubro', 'Novembro',
            'Dezembro'],
        monthNamesShort: ['Jan', 'Fev', 'Mar', 'Abr', 'Mai', 'Jun', 'Jul',
            'Ago', 'Set', 'Out', 'Nov', 'Dez'],
        dayNames: ['Domingo', 'Segunda', 'Terça', 'Quarta', 'Quinta',
            'Sexta', 'Sábado'],
        dayNamesShort: ['Dom', 'Seg', 'Ter', 'Qua', 'Qui', 'Sex', 'Sáb'],
        dayNamesMin: ['D', 'S', 'T', 'Q', 'Q', 'S', 'S'],
        weekHeader: 'Semana',
        firstDay: 0,
        isRTL: false,
        showMonthAfterYear: false,
        yearSuffix: '',
        timeOnlyTitle: 'São Horas',
        timeText: 'Tempo',
        hourText: 'Hora',
        minuteText: 'Minuto',
        secondText: 'Segundo',
        ampm: false,
        month: 'Mês',
        week: 'Semana',
        day: 'Dia',
        allDayText: 'Todo o Dia'
    };
}

/**
 * Ocutar o Menu lateral esquerdo quando necessário
 * 
 * @returns {undefined}
 */
function ocultarMenu() {
    $('#barramenu').animate({"left": "-200px"}, "slow", function () {
        $('#barramenu').hide();
    });
}

/**
 * Executar ao iniciar a página (escondendo menus etc..)
 * 
 * @returns {undefined}
 */
function initTamplate() {
    $(document).ready(function () {
        $('#menupop').hide();
        $('#barramenu').hide();
        $('#barramenu').css("left", "-200px");
        $('#iniciarmenu').click(function () {
            if ($('#barramenu').is(':visible')) {
                ocultarMenu();
                $("#barramenu").css("display", "none");
            } else {
                $("#barramenu").css("display", "block");
                $('#barramenu').show();
                $('#barramenu').animate({"left": "0px"}, "slow");
            }
        });
    });
}

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
    $("#id").css("display", "block");
    $("#menupop").show("slow").mouseleave(function () {
        fecharMenupop();
    });
}

//Função logout - seta para sair (fechar menu):
function fecharMenupop() {
    if ($("#menupop").is(":visible")) {
        //Fechar menu com efeito slow:
        $("#menupop").hide("slow");
    }
    $("#id").css("display", "none");
}

//InputTexts com UpperCase (Classe criada (ui-text-uppercase) -> Declarar nos inputs
$(function () {
    $(document).ready(function () {
        document.querySelectorAll(".ui-text-uppercase")
                .forEach((current) => {
                    current.addEventListener("input", function (event) {
                        event.target.value = event.target.value.toLocaleUpperCase();
                    });
                });
    });
});

//Redireciona para uma página especifica:
function redirecionarPagina(contextPath, pagina) {
    document.location = (contextPath + pagina + ".jsf");
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