(function () {
    if (document.getElementById([[${eventId}]]) === null) {
        Array.prototype.slice.call(document.querySelectorAll('body script'))
            .filter(function (el) {
                return el.src.indexOf([[${eventId}]]) !== -1
            })
            .forEach(function (el) {
                var rootWrap = document.createElement('div')
                rootWrap.className = 'promotion-builder-root-wrap';

                var promotionWrap = document.createElement('div')
                promotionWrap.id = [[${eventId}]];

                promotionWrap.innerHTML = `<h2>` + [[${title}]] + `</h2>`;

                rootWrap.appendChild(promotionWrap);

                el.parentNode.insertBefore(rootWrap, el)
            });
    }
}());
