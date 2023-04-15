var arrayIdsElementsPage = new Array;
var idundefined = 'idundefined';
var classTypeString = 'java.lang.String';
var classTypeLong = 'java.lang.Long';
var classTypeDate = 'java.util.Date';
var classTypeBoolean = 'java.lang.Boolean';
var classTypeBigDecimal = 'java.math.BigDecimal';

function reloadPage() {
	$(function() {
		location.reload();
	});
	
}	

function validaDescricao(descricao) {
	if (descricao === ' ' || descricao.trim() === '') {
		return "Descrição não foi informada.";
	}
	 else {
		return descricao;
	}
}


// invalida a sessï¿½o do spring security
function logout(contextPath) {
	
	document.location =	 contextPath + '/j_spring_security_logout';
	var post = 'invalidar_session';
	$.ajax(
		{ 
		  type: "POST", 
		  url: post
		});
}

/**
 * Usada apenas para o menu do sistema Limpar variaveis por ajax e redireciona
 * sempre a pagina
 * 
 * @param context
 * @param pagina
 * @param post
 */
function redirecionarPage(context, pagina, post) { 
	pagina = pagina + post + ".jsf";
	$.ajax(
			{ type: "POST",
			  url: post
			}).always(function(resposta) { 
					document.location = context + pagina;
			});
}

function redirecionarPagina(context, pagina) { 
	pagina = pagina + ".jsf";
	document.location = context + pagina;
}

function invalidarSession(context, pagina) { 
     document.location = (context + pagina + ".jsf");
}

function permitNumber(e) {
	var unicode = e.charCode ? e.charCode : e.keyCode;
	if (unicode != 8 && unicode != 9) {
		if (unicode < 48 || unicode > 57) {
			return false;
		}
	}
}


function naoPermiteEntradaDeDados(e) {
	return false;
}



function permitAlphaNumerico(e) {
	var unicode = e.charCode ? e.charCode : e.keyCode;
	if (unicode != 8 && unicode != 9) {
		return true;
	}
}

function permitDecimal(e) {
	var unicode = e.charCode ? e.charCode : e.keyCode;
	if (unicode != 8 && unicode != 9 && unicode != 46) {
		if (unicode < 48 || unicode > 57) {
			return false;
		}
	}
}

function addMascaraDecimalMonetaria(id) { 
	var id = getValorElementPorId(id);
	if (id != idundefined) {
		jQuery(function($){
			$("#"+id).maskMoney({precision:2, decimal:",", thousands:"."}); 
		});	
	}
	
}

function validarSenhaLogin() {
	j_username = document.getElementById('j_username').value;
	j_password = document.getElementById('j_password').value;

	if (j_username === null || j_username.trim() === '') {
		alert("Informe o Login.");
		 $("#j_username").focus();
		return false;
	}

	if (j_password === null || j_password.trim() === '') {
		alert("Informe a Senha.");
		 $("#j_password").focus();
		return false;
	}

	return true;

}

function initTamplete() {
$(document).ready(function() {
	  $('#menupop').hide();
	  $('#barramenu').hide();
	  $('#barramenu').css("left", "-200px");
	  $('#iniciarmenu').click(function() {
	  	if ($('#barramenu').is(':visible')) {
	  	  ocultarMenu();
	  	} else {
	  	  $('#barramenu').show();
	  	  $('#barramenu').animate({"left":"0px"}, "slow");	
	  	}
	  });
	});
}


function ocultarMenu() {
	  $('#barramenu').animate({"left":"-200px"}, "slow", function() {
	  	$('#barramenu').hide();
	  });
	}
	
	function abrirMenupop() {
	  $('#menupop').show('slow').mouseleave(function() {
	  	fecharMenupop();
	  });
	}
	
	function fecharMenupop() {
	  if ($("#menupop").is(":visible")) {
	  	$('#menupop').hide('slow');
	  }
	}

	function fecharPesquisa() {
		$('#paginapesquisa').html('&nbsp;');
		$('#valorpesquisa').val('');
		$('#dialogopesquisa').hide();
	}
	

	function localeData_pt_br() {
		PrimeFaces.locales['pt'] = {
			closeText : 'Fechar',
			prevText : 'Anterior',
			nextText : 'Próximo',
			currentText : 'Começo',
			monthNames : [ 'Janeiro', 'Fevereiro', 'Marcio', 'Abril', 'Maio',
					'Junho', 'Julho', 'Agosto', 'Setembro', 'Outubro', 'Novembro',
					'Dezembro' ],
			monthNamesShort : [ 'Jan', 'Fev', 'Mar', 'Abr', 'Mai', 'Jun', 'Jul',
					'Ago', 'Set', 'Out', 'Nov', 'Dez' ],
			dayNames : [ 'Domingo', 'Segunda', 'Terça', 'Quarta', 'Quinta',
					'Sexta', 'Sábado' ],
			dayNamesShort : [ 'Dom', 'Seg', 'Ter', 'Qua', 'Qui', 'Sex', 'Sáb' ],
			dayNamesMin : [ 'D', 'S', 'T', 'Q', 'Q', 'S', 'S' ],
			weekHeader : 'Semana',
			firstDay : 0,
			isRTL : false,
			showMonthAfterYear : false,
			yearSuffix : '',
			timeOnlyTitle : 'São Horas',
			timeText : 'Tempo',
			hourText : 'Hora',
			minuteText : 'Minuto',
			secondText : 'Segundo',
			ampm : false,
			month : 'Mês',
			week : 'Semana',
			day : 'Dia',
			allDayText : 'Todo o Dia'
		};

	}

function validarCampoPesquisa(valor) {
	if ( valor != undefined  &&  valor.value != undefined ) {
		if (valor.value.trim() === '') {
			valor.value = '';
		}else {
			valor.value = valor.value.trim();
		}
	}
}

/**
 * Carrega um array global com os ids de todos os componentes da pagina Para ter
 * faciliades em obtencao de valores dos componentes bem como trabalhar com ajax
 */
function carregarIdElementosPagina() {
	 arrayIdsElementsPage = new Array;
	 for (form = 0 ; form <= document.forms.length; form++){
		 var formAtual = document.forms[form];
		 if (formAtual != undefined) {
			 for (i = 0; i< document.forms[form].elements.length; i++){
				 if(document.forms[form].elements[i].id != '') {
					 arrayIdsElementsPage[i] = document.forms[form].elements[i].id;
				 }
			 }	
		 }
	 }
}
/**
 * Retorno o valor do id do componente dentro do documento html passando como
 * parametro a descriï¿½ï¿½o do id declarada em jsf
 * 
 * @param id
 */
function getValorElementPorId(id) {
	 carregarIdElementosPagina();
	 for (i = 0; i< arrayIdsElementsPage.length; i++){
		 var valor =  ""+arrayIdsElementsPage[i];
		 if (valor.indexOf(id) > -1) {
			return valor;
	}
  }	
	 return idundefined;
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
	if (id.trim() != idundefined) {
		 return PrimeFaces.escapeClientId(id);
	}
	
	 return idundefined;
}

/**
 * Adiciona foco ao campo passado como paramentro
 * 
 * @param campo
 */
function addFocoAoCampo(campo) {
	var id = getValorElementPorId(campo);
	if (id != idundefined) {
		document.getElementById(getValorElementPorId(id)).focus();
	}
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
	var id = getValorElementPorIdJQuery('valorPesquisa');
	var vals = elemento.split("*");
	var campoBanco = vals[0];
	var typeCampo = vals[1];
	
	$(id).unmask();
	$(id).unbind("keypress"); 
	$(id).unbind("keyup");
	$(id).unbind("focus");
	$(id).val('');
	if (id != idundefined) {
		jQuery(function($) {
			if (typeCampo === classTypeLong) {
				$(id).keypress(permitNumber);
			}
			else if (typeCampo === classTypeBigDecimal) {	
				$(id).maskMoney({precision:4, decimal:",", thousands:"."}); 
			}
			else if (typeCampo === classTypeDate) {
				$(id).mask('99/99/9999');
			}
			else {
				$(id).unmask();
				$(id).unbind("keypress");
				$(id).unbind("keyup");
				$(id).unbind("focus");
				$(id).val('');
			}
			addFocoAoCampo("valorPesquisa");
		});
	}
}

function permitirApenasNumero(id) {
	var id = getValorElementPorIdJQuery(id);
	$(id).keypress(permitNumber);
}

/**
 * Add bairro selecionado na tela de filial
 * 
 * @param objeto
 */
function addBairroSelecionadoFilial(objeto) {
	var bairroObj = JSON.parse(objeto);
	$("#bai_codigo").val(bairroObj.bai_codigo);
	$("#bai_descricao").val(validaDescricao(bairroObj.bai_descricao));
	addBairroFilial(''+bairroObj.bai_codigo);
}

/**
 * Add bairro selecionado na tela de funcionrio
 * 
 * @param objeto
 */
function addBairroSelecionadoFunc(objeto) {
	var bairroObj = JSON.parse(objeto);
	$("#bai_codigo").val(bairroObj.bai_codigo);
	$("#bai_descricao").val(validaDescricao(bairroObj.bai_descricao));
	addBairroFunc(''+bairroObj.bai_codigo);
}

/**
 * Add bairro seleiconado na tela de entidade
 * 
 * @param objeto
 */
function addBairroSelecionadoEntidade(objeto) {
	var bairroObj = JSON.parse(objeto);
	$("#bai_codigo").val(bairroObj.bai_codigo);
	$("#bai_descricao").val(validaDescricao(bairroObj.bai_descricao));
	addBairroEntidade(''+bairroObj.bai_codigo);
}

/**
 * add filial selecionada na tela de entidade
 * 
 * @param objeto
 */
function addFilialSelecionadoEntidade(objeto) {
	var filialObj = JSON.parse(objeto);
	$("#fil_codigo").val(filialObj.fil_codigo);
	$("#fil_descricao").val(validaDescricao(filialObj.fil_descricao));
	addFilialEntidade(''+filialObj.fil_codigo);
}

/**
 * add filial selecionada na tela de funcionario
 * 
 * @param objeto
 */
function addFilialSelecionadoFunc(objeto) {
	var filialObj = JSON.parse(objeto);
	$("#fil_codigo").val(filialObj.fil_codigo);
	$("#fil_descricao").val(validaDescricao(filialObj.fil_descricao));
	addFilialFunc(''+filialObj.fil_codigo);
}

function addFilialSelecionadoComissao(objeto) {
	var filialObj = JSON.parse(objeto);
	$("#fil_codigo").val(filialObj.fil_codigo);
	$("#fil_descricao").val(validaDescricao(filialObj.fil_descricao));
	addFilialComissao(''+filialObj.fil_codigo);
}

/**
 * Add cidade selecionada na tela de filial
 * 
 * @param objeto
 */
function addCidadeSelecionadoFilial(objeto) {
	var cidadeObj = JSON.parse(objeto);
	$("#cid_codigo").val(cidadeObj.cid_codigo);
	$("#cid_descricao").val(validaDescricao(cidadeObj.cid_descricao));
	addCidadeFilial(''+cidadeObj.cid_codigo);
}

function addDestinoSelecionadoMsg(objeto) {
	var entObj = JSON.parse(objeto);
	$("#usr_destino").val(entObj.ent_codigo);
	$("#loginDestino").val(validaDescricao(entObj.ent_login));
	addDestinoMsg(''+entObj.ent_codigo);
}

function addDestinoSelecionadoMsgDialog(objeto) {
	var entObj = JSON.parse(objeto);
	$("#usr_destinoMsgDialog").val(entObj.ent_codigo);
	$("#loginDestinoMsgDialog").val(validaDescricao(entObj.ent_login));
	addDestinoMsg(''+entObj.ent_codigo);
}

function addFuncSelecionadoComissao(objeto) {
	var entObj = JSON.parse(objeto);
	$("#ent_codigo").val(entObj.ent_codigo);
	$("#loginDestino").val(validaDescricao(entObj.ent_login));
	addFuncComissao(''+entObj.ent_codigo);
}

function addContrutoraSelecionadoEmpreendimento(objeto) {
	var entObj = JSON.parse(objeto);
	$("#ent_codigo").val(entObj.ent_codigo);
	$("#descricaoConstrutora").val(validaDescricao(entObj.ent_nomefantasia));
	addConstrutoraEmpreendimento(''+entObj.ent_codigo);
}

function addResponsavelSelecionadoTitulo(objeto) {
	var entObj = JSON.parse(objeto);
	$("#responsavelCodigo").val(entObj.ent_codigo);
	$("#responsavelNome").val(validaDescricao(entObj.ent_nomefantasia));
	addResponsavelTitulo(''+entObj.ent_codigo);
}



function addContrutoraSelecionadoVendedor2(objeto) {
	var entObj = JSON.parse(objeto);
	$("#ent_codigo").val(entObj.ent_codigo);
	$("#descricaoConstrutora").val(validaDescricao(entObj.ent_nomefantasia));
}

function addVendedorSelecionadoConstrutora(objeto) {
	var entObj = JSON.parse(objeto);
	$("#codigoVendedor").val(entObj.ent_codigo);
	$("#descricaoVendedor").val(validaDescricao(entObj.ent_nomefantasia));
	addVendedorContrutora(''+entObj.ent_codigo);
}



/**
 * Add cidade selecionada na tela de entidade
 * 
 * @param objeto
 */
function addCidadeSelecionadoEntidade(objeto) {
	var cidadeObj = JSON.parse(objeto);
	$("#cid_codigo").val(cidadeObj.cid_codigo);
	$("#cid_descricao").val(validaDescricao(cidadeObj.cid_descricao));
	addCidadeEntidade(''+cidadeObj.cid_codigo);
}


/**
 * Add cidade selecionada na tela de entidade
 * 
 * @param objeto
 */
function addCidadeSelecionadoFunc(objeto) {
	var cidadeObj = JSON.parse(objeto);
	$("#cid_codigo").val(cidadeObj.cid_codigo);
	$("#cid_descricao").val(validaDescricao(cidadeObj.cid_descricao));
	addCidadeFunc(''+cidadeObj.cid_codigo);
}

/**
 * Pesquisa filial ao informar o id
 * 
 * @param id
 */
function pesquisarFilialPerderFoco(id) {
	if (id.trim() != '') {
	 statusDialog.show();
	 $("#fil_descricao").val('');
	 $.get("findFilial?codFilial=" + id, function(resposta) {
	        if (resposta != 'erro' && resposta.trim() != ''){
	        	var filialObj = JSON.parse(resposta);
	        	$("#fil_codigo").val(filialObj.fil_codigo);
	        	$("#fil_descricao").val(validaDescricao(filialObj.fil_descricao));
	        }
	   })
	   .always(function() { 
		   statusDialog.hide();
		});
	}
}


function pesquisarResponsavelPerderFoco(id) {
	if (id.trim() != '') {
	 statusDialog.show();
	 $("#responsavelNome").val('');
	 $.get("findResponsavel?codResponsavel=" + id, function(resposta) {
	        if (resposta != 'erro' && resposta.trim() != ''){
	        	var respObj = JSON.parse(resposta);
	        	$("#responsavelCodigo").val(respObj.ent_codigo);
	        	$("#responsavelNome").val(validaDescricao(respObj.ent_nomefantasia));
	        }
	   })
	   .always(function() { 
		   statusDialog.hide();
		});
	}
}



/**
 * Pesquisa bairro ao informar o id
 * 
 * @param id
 */
function pesquisarBairroPerderFoco(id) {
	if (id.trim() != '') {
	 statusDialog.show();
	 $("#bai_descricao").val('');
	 $.get("findBairro?codBairro=" + id, function(resposta) {
	        if (resposta != 'erro' && resposta.trim() != ''){
	        	var bairroObj = JSON.parse(resposta);
	        	$("#bai_codigo").val(bairroObj.bai_codigo);
	        	$("#bai_descricao").val(validaDescricao(bairroObj.bai_descricao));
	        }
	   })
	   .always(function() { 
		   statusDialog.hide();
		});
	}
}

function pesquisarConstrutoraPerderFoco2(id) {
	if (id.trim() != '') {
		 statusDialog.show();
		 $("#descricaoConstrutora").val('');
		 $.get("findConstrutora2?codConstrutora=" + id, function(resposta) {
		        if (resposta != 'erro' && resposta.trim() != ''){
		        	var entObj = JSON.parse(resposta);
		        	$("#ent_codigo").val(entObj.ent_codigo);
		        	$("#descricaoConstrutora").val(validaDescricao(entObj.ent_nomefantasia));
		        }
		   })
		   .always(function() { 
			   statusDialog.hide();
			});
		}
}

function pesquisarConstrutoraPerderFoco(id) {
	if (id.trim() != '') {
		 statusDialog.show();
		 $("#descricaoConstrutora").val('');
		 $.get("findConstrutora?codConstrutora=" + id, function(resposta) {
		        if (resposta != 'erro' && resposta.trim() != ''){
		        	var entObj = JSON.parse(resposta);
		        	$("#ent_codigo").val(entObj.ent_codigo);
		        	$("#descricaoConstrutora").val(validaDescricao(entObj.ent_nomefantasia));
		        }
		   })
		   .always(function() { 
			   statusDialog.hide();
			});
		}
}

function pesquisarVendedorPerderFoco(id) {
	if (id.trim() != '') {
		 statusDialog.show();
		 $("#codigoVendedor").val('');
		 $.get("findVendedor?codigoVendedor=" + id, function(resposta) {
		        if (resposta != 'erro' && resposta.trim() != ''){
		        	var entObj = JSON.parse(resposta);
		        	$("#codigoVendedor").val(entObj.ent_codigo);
		        	$("#descricaoVendedor").val(validaDescricao(entObj.ent_nomefantasia));
		        }
		   })
		   .always(function() { 
			   statusDialog.hide();
			});
		}
}


function pesquisarUserDestinoPerderFoco(id) {
	if (id.trim() != '') {
	 statusDialog.show();
	 $("#loginDestino").val('');
	 $.get("findUserDestino?codEntidade=" + id, function(resposta) {
	        if (resposta != 'erro' && resposta.trim() != ''){
	        	var entidadeObj = JSON.parse(resposta);
	        	$("#usr_destino").val(entidadeObj.ent_codigo);
	        	$("#loginDestino").val(validaDescricao(entidadeObj.ent_login));
	        }
	   })
	   .always(function() { 
		   statusDialog.hide();
		});
	}
}

function pesquisarUserDestinoPerderFocoDialog(id) {
	if (id.trim() != '') {
	 statusDialog.show();
	 $("#loginDestinoMsgDialog").val('');
	 $.get("findUserDestino?codEntidade=" + id, function(resposta) {
	        if (resposta != 'erro' && resposta.trim() != ''){
	        	var entidadeObj = JSON.parse(resposta);
	        	$("#usr_destinoMsgDialog").val(entidadeObj.ent_codigo);
	        	$("#loginDestinoMsgDialog").val(validaDescricao(entidadeObj.ent_login));
	        }
	   })
	   .always(function() { 
		   statusDialog.hide();
		});
	}
}


function pesquisarFuncionarioComissaoPerdeFoco(id) {
	if (id.trim() != '') {
	 statusDialog.show();
	 $("#ent_codigo").val('');
	 $.get("findFuncComissao?codFuncionario=" + id, function(resposta) {
	        if (resposta != 'erro' && resposta.trim() != ''){
	        	var entidadeObj = JSON.parse(resposta);
	        	$("#ent_codigo").val(entidadeObj.ent_codigo);
	        	$("#loginDestino").val(validaDescricao(entidadeObj.ent_login));
	        }
	   })
	   .always(function() { 
		   statusDialog.hide();
		});
	}
}


function pesquisarEntidadePerderFoco(id) {
	if (id.trim() != '') {
	 statusDialog.show();
	 $("#loginDestino").val('');
	 $.get("findEntidade?codEntidade=" + id, function(resposta) {
	        if (resposta != 'erro' && resposta.trim() != ''){
	        	var entidadeObj = JSON.parse(resposta);
	        	$("#usr_destino").val(entidadeObj.ent_codigo);
	        	$("#loginDestino").val(validaDescricao(entidadeObj.ent_login));
	        }
	   })
	   .always(function() { 
		   statusDialog.hide();
		});
	}
}

/**
 * Pesquisa cidade ao perder o foco informando o id
 * 
 * @param id
 */
function pesquisarCidadePerderFoco(id) {
	if (id.trim() != '') {
	 statusDialog.show();
	 $("#cid_descricao").val('');
	 $.get("findCidade?codCidade=" + id, function(resposta) {
	        if (resposta != 'erro' && resposta.trim() != ''){
	        	var cidadeObj = JSON.parse(resposta);
	        	$("#cid_codigo").val(cidadeObj.cid_codigo);
	        	$("#cid_descricao").val(validaDescricao(cidadeObj.cid_descricao));
	        }
	   })
	   .always(function() { 
		   statusDialog.hide();
		});
	}
}

/**
 * add bairro selecionado no funcionario sendo cadastrado
 * 
 * @param id
 */
function addBairroFunc(id) {
	if (id.trim() != '') {
		 $.get("addBairroFunc?codBairro=" + id);
	}
} 


/**
 * add bairro selecionado a filial sendo cadastrado
 * 
 * @param id
 */
function addBairroFilial(id) {
	if (id.trim() != '') {
		 $.get("addBairroFilial?codBairro=" + id);
	}
} 


function addDestinoMsg(id) {
	if (id.trim() != '') {
		 $.get("addDestinoMsg?codEntidade=" + id);
	}
} 

function addFuncComissao(id) {
	if (id.trim() != '') {
		 $.get("addFuncComissao?codEntidade=" + id);
	}
} 


function addConstrutoraEmpreendimento(id) {
	if (id.trim() != '') {
		 $.get("addConstrutoraEmpreendimento?codConstrutora=" + id);
	}
} 


function addConstrutoraVendedor(id) {
	if (id.trim() != '') {
		 $.get("addConstrutoraVendedor?codConstrutora=" + id);
	}
} 

function addVendedorContrutora(id) {
	if (id.trim() != '') {
		 $.get("addVendedorContrutora?codVendedor=" + id);
	}
} 


/**
 * Add bairro selecionado na entidade sendo cadastrada
 * 
 * @param id
 */
function addBairroEntidade(id) {
	if (id.trim() != '') {
		 $.get("addBairroEntidade?codBairro=" + id);
	}
} 


/**
 * Add filial selecionada na entidade sendo cadastrada
 * 
 * @param id
 */
function addFilialEntidade(id) {
	if (id.trim() != '') {
		 $.get("addFilialEntidade?codFilial=" + id);
	}
} 


function addResponsavelTitulo(id) {
	if (id.trim() != '') {
		 $.get("addResponsavelTitulo?codResponsavel=" + id);
	}
} 



/**
 * Add filial selecionada no funcionario sendo cadastrada
 * 
 * @param id
 */
function addFilialFunc(id) {
	if (id.trim() != '') {
		 $.get("addFilialFunc?codFilial=" + id);
	}
} 

function addFilialComissao(id) {
	if (id.trim() != '') {
		 $.get("addFilialComissao?codFilial=" + id);
	}
} 




/**
 * Add cidade selecinada na filial sendo cadastrada
 * 
 * @param id
 */
function addCidadeFilial(id) {
	if (id.trim() != '') {
		 $.get("addCidadeFilial?codCidade=" + id);
	}
} 


/**
 * Add cidade selecinada no funcionario sendo cadastrada
 * 
 * @param id
 */
function addCidadeFunc(id) {
	if (id.trim() != '') {
		 $.get("addCidadeFunc?codCidade=" + id);
	}
} 


/**
 * Add cidade selecionada na entidade sendo cadastrada
 * 
 * @param id
 */
function addCidadeEntidade(id) {
	if (id.trim() != '') {
		 $.get("addCidadeEntidade?codCidade=" + id);
	}
} 

function verificaMsgNaoLidas() {
	 $.get("verificaMsgNaoLidas?isProcessoFacesJsf=false", function(resposta) {
		 var respObj = JSON.parse(resposta);
		 if (respObj.quantidadeMsgNaoLida != '0'){
			 	$('#avisomensagem').show();
				$('#contadormensagem').html(respObj.quantidadeMsgNaoLida);
	      } else {
				$('#avisomensagem').hide();// esconde o icone de carta
				$('#contadormensagem').html("&nbsp;");
		 }
	  }); 
} 


function alterarSenha(context) {
	 statusDialog.show();
	 	document.location =	 context + "/cadastro/alterar_senha.jsf";
	 statusDialog.hide();
}

function marcarDesmarcarLido(men_codigo, context) {
	if (men_codigo != null && men_codigo != '') {
	 $.get("marcarDesmarcarLido?men_codigo=" + men_codigo, function(resposta) {
	   })
	   .always(function() { 
		   document.location = context + "/cadastro/msg_recebidas.jsf";
	   });
	}
	else {
		alert("Selecione uma mensagem.");
	}
}

function responderMsg(context, destino) {
	 $.get("responderMsg?destino=" + destino, function(resposta) {
	   })
	   .always(function() { 
		   document.location = context + "/cadastro/cad_mensagem.jsf";
	   });
}

function copiarValorFantasiaRazao(campo) {
	var idCampoDestino = getValorElementPorIdJQuery('ent_razao');
	var textParaCopia = campo.value;
	
	var textCampoDestino = $(idCampoDestino).val();
	
	if (textCampoDestino === null || textCampoDestino === '' || textCampoDestino === ' '){
		$(idCampoDestino).val(textParaCopia);
	}
	
}

function confirmaLeituraMsg(men_codigo) {
	
	 $.get("confirmaLeituraMsg?men_codigo=" + men_codigo, function(resposta) {
		 // alguma ação aqui se precisar
		 reloadPage();
		}).fail(function() {
		    alert( "Erro ao enviar confirmação de leitura da mensagem." );
		});
	
}

// Faz com que a tecla enter tenha efeito de TAB pulando de campo em campo
function gerenciaTeclaEnter() {
	$(document).ready(function() {
		$('input').keypress(function(e) {
			var code = null;
			code = (e.keyCode ? e.keyCode : e.which);
			return (code === 13) ? false : true;

		});

		$('input[type=text]').keydown(function(e) {
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

function processaDelete(e) {
	
	 
	/*
	 * var rows = document.getElementsByTagName('tr');
	 * 
	 * for(var x = 0, xLength = rows.length; x < xLength; x++) {
	 * 
	 * alert('rowIndex=' + rows[x].rowIndex);
	 *  }
	 */
	
	$(document).ready(function(){
	     $("table>tbody>tr").each(function(index, elemento){
	    	 
	    	 var selecionado = $(this).attr('aria-selected'); 
	    	 
	    	 if(selecionado != undefined 
	    			 && selecionado != 'undefined' 
	    			 && selecionado === 'true' || selecionado === true){
	    		 
	    	
	    		 //$(this).attr("onkeypress", "javascript:alert('kkk')");
	    		// $(this).attr("onkeydown", "javascript:alert('kkk')");
	    		 //$(this).attr("onkeyup", "javascript:alert('kkk')");
	    		 //$(this).attr("ondblclick", "javascript:alert('kkk')");
	    		 //alert($(this).attr('data-ri') + " - " + $(this).attr('data-rk')); 
	    	 }
//	         $(elemento).bind('click', function(){
	             //$(this).css('background-color', 'red');
	//         });
	     });
	});
}

function ocultaDataNacimento(value){
	  if (value === 'TIPO_PESSOA_JURIDICA'){
			
			$("#labelDataNasc").hide(); 
			
			var id = getValorElementPorIdJQuery('ent_datanascimento');
			$("#ent_datanascimento").removeClass('calendar');
			$("#ent_datanascimento").addClass('calendarOculta');
			$(id).hide();
			
			$("#msgent_datanascimento").hide();
			
	  }else {
			$("#labelDataNasc").show();
			
			var id = getValorElementPorIdJQuery('ent_datanascimento');
			$("#ent_datanascimento").removeClass('calendarOculta');
			$("#ent_datanascimento").addClass('calendar');
			$(id).show();
			
			$("#msgent_datanascimento").show();	
	  }
	}




function invocaApplet(context) {
	
	   //Faz algo com ajax...
	    
		var url = context + "/applet/imprimir.jsp?impressoraImprimir=" + null;// passando null para pegar a padrão
		
		var title = "Imprimindo...";
		var w = "150"; 
		var h = "130";
	    var dualScreenLeft = window.screenLeft != undefined ? window.screenLeft : screen.left;
	    var dualScreenTop = window.screenTop != undefined ? window.screenTop : screen.top;

	    width = window.innerWidth ? window.innerWidth : document.documentElement.clientWidth ? document.documentElement.clientWidth : screen.width;
	    height = window.innerHeight ? window.innerHeight : document.documentElement.clientHeight ? document.documentElement.clientHeight : screen.height;

	    var left = ((width / 2) - (w / 2)) + dualScreenLeft;  
	    var top = ((height / 2) - (h / 2)) + dualScreenTop;  
	    window.open(url, title, 'scrollbars=true, width=' + w + ', height=' + h + ', top=' + top + ', left=' + left);
}

function invocaAppletFileLocal(context) {
	var url = context + "/applet/ler_file_local.jsp"; 

	var title = "Lendo arquivo local...";
	var w = "220";
	var h = "200";
    var dualScreenLeft = window.screenLeft != undefined ? window.screenLeft : screen.left;
    var dualScreenTop = window.screenTop != undefined ? window.screenTop : screen.top;

    width = window.innerWidth ? window.innerWidth : document.documentElement.clientWidth ? document.documentElement.clientWidth : screen.width;
    height = window.innerHeight ? window.innerHeight : document.documentElement.clientHeight ? document.documentElement.clientHeight : screen.height;

    var left = ((width / 2) - (w / 2)) + dualScreenLeft;  
    var top = ((height / 2) - (h / 2)) + dualScreenTop;  
    window.open(url, title, 'scrollbars=true, width=' + w + ', height=' + h + ', top=' + top + ', left=' + left);
}

function respostaApplet(respostaProperties) {
	respostaPropertiesElement = document.getElementById("respostaPropertiesElement");
	if (respostaProperties != ''){
		respostaPropertiesElement.value = respostaProperties;
	}
	// Faz qualquer coisa aqui
}

function executeApplet(context) {
	contextApp = context;
	var attributes = {
		code : 'JalisApplet.class',
		archive : 'jalisApplet.jar',
		codebase : context + '/applet/',
		type : 'applet',
		name : 'jalisApplet',
		width : 0,
		height : 0
	};
	var parameters = {
		parametroApplet : 'parametroApplet teste valor',
		Permissions : 'all-permissions'
	};
	var version = '1.6';
	deployJava.runApplet(attributes, parameters, version);
}

/**
 * Mostra erro do applet
 * @param erro
 */
function erroApplet(erro) { 
	alert(erro);
}

