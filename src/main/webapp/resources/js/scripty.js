var arrayIdsElementsPage = new Array;
var classTypeString = 'java.lang.String';
var classTypeLong = 'java.lang.Long';
var classTypeDate = 'java.util.Date';
var classTypeBoolean = 'java.lang.Boolean';
var classTypeBigDecimal = 'java.math.BigDecimal';

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

    //Caso quiser debugar o JavaScript vai parar aqui ao rodar:
    //MUITO BOM!!!!!!!
//    debugger;

    $.ajax({
        type: 'POST',
        url: urlPost
    }).always(function resposta() {
        //Executar isso sempre depois que a função ajax post for executada, independente se deu sucesso ou não!!! 
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
function setUppercaseInputTextJs() {
    $(document).ready(function () {
        document.querySelectorAll(".ui-text-uppercase")
                .forEach((current) => {
                    current.addEventListener("input", function (event) {
                        event.target.value = event.target.value.toLocaleUpperCase();
                    });
                });
    });
}

//InputTexts com LowerCase (Classe criada (ui-text-lowercase) -> Declarar nos inputs
function setLowercaseInputTextJs() {
    $(document).ready(function () {
        document.querySelectorAll(".ui-text-lowercase")
                .forEach((current) => {
                    current.addEventListener("input", function (event) {
                        event.target.value = event.target.value.toLocaleLowerCase();
                    });
                });
    });
}

/**
 * Adiciona foco ao campo passado como paramentro
 * 
 * @param campo
 */
function addFocoAoCampo(campo) {
    var id = getValorElementPorId(campo);
    if (id !== null
            && id !== undefined) {
        document.getElementById(id).focus();
    }
}

/**
 * Tecla enter funciona como TAB nos campos de input
 * 
 * @returns {undefined}
 */
function tabInTeclaEnter() {
    $(document).ready(function () {
        $('input').keypress(function (e) {
            var code = null;
            code = (e.keyCode ? e.keyCode : e.which);
            return (code === 13) ? false : true;

        });

        $('input[type=text]').keydown(function (e) {
            // Obter o próximo índice do elemento de entrada de texto
            var next_idx = $('input[type=text]').index(this) + 1;

            // Obter o número de elemento de entrada de texto em um documento html
            var tot_idx = $('body').find('input[type=text]').length;

            // Entra na tecla no código ASCII
            if (e.keyCode === 13) {
                if (tot_idx === next_idx)
                    // Vá para o primeiro elemento de texto
                    $('input[type=text]:eq(0)').focus();
                else
                    // Vá para o elemento de entrada de texto seguinte
                    $('input[type=text]:eq(' + next_idx + ')').focus();
            }
        });
    });
}

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

/**
 * Gera automaticamente mascara para a tela de pesquisa var classTypeString =
 * 'java.lang.String'; var classTypeLong = 'java.lang.Long'; var classTypeDate =
 * 'java.util.Date'; var classTypeBoolean = 'java.lang.Boolean'; var
 * classTypeBigDecimal = 'java.math.BigDecimal';
 * 
 * @param elemento
 */
function addMascaraPesquisa(elemento) {
    var id = getValorElementPorIdJQuery('txtValorPesquisa');
    //Separação por '*' que definimos no converter do ObjetoCampoConsultaConverter
    var vals = elemento.split("*");
    var campoBanco = vals[0];
    var typeCampo = vals[1];

    //Retira a mascará padrão para não deixar bugado as mascáras quando troca de Classe para o componente:
    jQuery(id).unmask();
    //retira a mascára também do money para não deixar bugado as mascáras quando troca de Classe para o componente:
    jQuery(id).maskMoney('destroy');
    $(id).unbind("keypress");
    $(id).unbind("keyup");
    $(id).unbind("focus");
    $(id).val('');
    if (id !== undefined) {
        jQuery(function ($) {
            if (typeCampo === classTypeLong) {
                jQuery(id).unmask();
                jQuery(id).maskMoney('destroy');
                $(id).unbind("keypress");
                $(id).unbind("keyup");
                $(id).unbind("focus");
                $(id).val('');
                $(id).keypress(permitNumber);
            } else if (typeCampo === classTypeBigDecimal) {
                jQuery(id).unmask();
                jQuery(id).maskMoney('destroy');
                $(id).unbind("keypress");
                $(id).unbind("keyup");
                $(id).unbind("focus");
                $(id).val('');
                $(id).maskMoney({precision: 2, decimal: ",", thousands: "."});
            } else if (typeCampo === classTypeDate) {
                jQuery(id).unmask();
                jQuery(id).maskMoney('destroy');
                $(id).unbind("keypress");
                $(id).unbind("keyup");
                $(id).unbind("focus");
                $(id).val('');
                $(id).mask('99/99/9999');
            } else {
                jQuery(id).unmask();
                jQuery(id).maskMoney('destroy');
                $(id).unbind("keypress");
                $(id).unbind("keyup");
                $(id).unbind("focus");
                $(id).val('');
            }

            addFocoAoCampo("txtValorPesquisa");
        });
    }
}

function permitNumber(e) {
    var unicode = e.charCode ? e.charCode : e.keyCode;
    if (unicode !== 8 && unicode !== 9) {
        if (unicode < 48 || unicode > 57) {
            return false;
        }
    }
}

/**
 * primefaces.js cï¿½digo fonte
 * escapeClientId:function(a){return"#"+a.replace(/:/g,"\\:")}
 * 
 * @param id
 * @returns id
 */
function getValorElementPorIdJQuery(id) {
    var id = getValorElementPorId(id);
    if (id.trim() !== undefined) {
        return PrimeFaces.escapeClientId(id);
    }

    return undefined;
}

/**
 * Método para caso for fazer a busca pelo usuário destino via javascript vai chamar um método mapeado via Spring trazendo o objeto Entidade em JSON
 * 
 * @param {type} codUser
 * @returns {undefined}
 */
function pesquisaUserDestinoPerderFocoDialog(codUser) {
    if (codUser !== null
            && codUser.trim() !== '') {
        $("#txtUsuarioDestinoCodigo").val('');
        $("#txtUsuarioDestinoLogin").val('');

        $.get("buscarUsuarioDestinoMsg?codEntidade=" + codUser, function (resposta) {
            if (resposta !== null
                    && resposta.trim() !== '') {
                let entidade = JSON.parse(resposta);

                $("#txtUsuarioDestinoCodigo").val(entidade.codigo);
                $("#txtUsuarioDestinoLogin").val(entidade.login);
            }
        });
    }
}