(function () {
    var event = /*[[${event}]]*/ {
        "id": "65bf7a57fe10c00a7e8a9021",
        "title": "카모아 신규회원 이벤트",
        "description": "신규회원 대상 쿠폰팩 지급 이벤트",
        "bannerImage": "https://d1y0pslxvt2ep5.cloudfront.net/event/banner/banner_83_20231016032640.png",
        "blocks": [
            {
                "image": "https://d1y0pslxvt2ep5.cloudfront.net/event/content/content_83_1_20231016032640.png",
                "blockType": "IMAGE"
            },
            {
                "image": "https://d1y0pslxvt2ep5.cloudfront.net/event/content/content_83_2_20231016032640.png",
                "blockType": "LINK",
                "url": "https://carmore.kr/home/",
                "openType": "BLANK"
            },
            {
                "image": "https://d1y0pslxvt2ep5.cloudfront.net/event/content/content_83_5_20231016032640.png",
                "blockType": "SCRIPT",
                "script": "alert(\"hello\");"
            },
        ],
        "grades": [
            "MEMBER"
        ],
        "startDateTime": "2020-05-21T14:08:45",
        "endDateTime": "2030-07-19T10:21:25",
    };

    if (document.getElementById(`promotion_builder_wrap_${event.id}`) === null) {
        Array.prototype.slice.call(document.querySelectorAll('body script'))
            .filter(function (el) {
                return el.src.indexOf(`/${event.id}.js`) !== -1
            })
            .forEach(function (el) {
                var rootWrap = document.createElement('div')
                rootWrap.className = 'promotion-builder-root-wrap';

                var promotionWrap = document.createElement('div')
                promotionWrap.id = `promotion_builder_wrap_${event.id}`;

                event.blocks.forEach(((block, index) => {
                    if (block.blockType === 'IMAGE') {
                        promotionWrap.innerHTML += `<img src="${block.image}" alt="Image ${index + 1}" style="display: block; width: 100%;">`;
                    } else if (block.blockType === 'LINK') {
                        promotionWrap.innerHTML += `<a href="${block.url}" target="${block.openType === 'BLANK' ? '_blank' : '_self'}" style="display: block; width: 100%;">
                                                        <img src="${block.image}" alt="Image ${index + 1}" style="display: block; width: 100%;">
                                                    </a>`;
                    } else if (block.blockType === 'SCRIPT') {
                        var scriptImgId = `promotion_builder_script_${event.id}_${index + 1}`;
                        document.body.addEventListener('click', function (event) {
                            if (event.target.id === scriptImgId) {
                                eval(block.script);
                            }
                        });

                        promotionWrap.innerHTML += `<img id="${scriptImgId}" src="${block.image}" alt="Image ${index + 1}" style="display: block; width: 100%; cursor: pointer;">`;
                    }
                }));

                rootWrap.appendChild(promotionWrap);
                el.parentNode.insertBefore(rootWrap, el);
            });
    }
}());
