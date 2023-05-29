$(function () {
    $("body").keydown(function (event) {
        const atalho = getTextoAtalhoPressionado(event);
        const botoes = getLocalAtalho().querySelectorAll('.ui-button, .ui-menuitem-link');

        for (let i = 0; i < botoes.length; i++) {
            let botao = botoes[i];

            if ($(botao).is(':visible') && !$(botao).is(':disabled')
                    && ((botao.getAttribute('class').includes('ui-button') && botao.textContent.includes(atalho))
                            || (botao.getAttribute('class').includes('ui-menuitem-link') && botao.getElementsByTagName('span')[0].textContent.includes(atalho)))) {

                botao.focus();
                botao.click();

                event.preventDefault();
                event.stopPropagation();

                break;
            }
        }
    });
});

function getTextoAtalhoPressionado(event) {
    const textoTecla = event.key === 'Escape' ? 'ESC'
            : event.key === 'Enter' ? 'ENTER'
            : event.key;

    return "(" + (event.ctrlKey === true ? "CTRL+" : "") + textoTecla + ")";
}

function getLocalAtalho() {
    const dialogs = document.querySelectorAll('.ui-dialog, .ui-confirm-dialog');

    if (dialogs.length > 0) {
        for (let i = (dialogs.length - 1); i >= 0; i--) {
            if (dialogs[i].style.visibility === 'visible') {
                return dialogs[i];
            }
        }
    }

    if (document.getElementsByClassName('ui-tabs') !== null
            && document.getElementsByClassName('ui-tabs')[0] !== undefined) {
        return document.getElementsByClassName('ui-tabs')[0];
    }

    if (document.getElementsByClassName('form') !== null
            && document.getElementsByClassName('form')[0] !== undefined) {
        return document.getElementsByClassName('form')[0];
    }

    return;
}