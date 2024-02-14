(function () {
    var event = /*[[${event}]]*/ {
        "id": "65bf7a57fe10c00a7e8a9021",
        "title": "카모아 신규회원 이벤트",
        "description": "신규회원 대상 쿠폰팩 지급 이벤트",
        "bannerImage": "https://d1y0pslxvt2ep5.cloudfront.net/event/banner/banner_83_20231016032640.png",
        "blocks": [
            {
                "tag": "image",
                "image": "https://d1y0pslxvt2ep5.cloudfront.net/event/content/content_83_1_20231016032640.png",
                "blockType": "IMAGE"
            },
            {
                "tag": "link",
                "image": "https://d1y0pslxvt2ep5.cloudfront.net/event/content/content_83_2_20231016032640.png",
                "blockType": "LINK",
                "url": "https://carmore.kr/home/",
                "openType": "BLANK"
            },
            {
                "tag": "script",
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

    function addScript(src) {
        var s = document.createElement('script');
        s.setAttribute('src', src);
        document.body.appendChild(s);
    }

    if (document.getElementById(`promotion_builder_wrap_${event.id}`) === null) {
        Array.prototype.slice.call(document.querySelectorAll('body script'))
            .filter(function (el) {
                return el.src.indexOf(`/${event.id}.js`) !== -1
            })
            .forEach(function (el) {
                addScript('https://unpkg.com/micromodal@0.4.10/dist/micromodal.min.js');

                // Promotion Builder Script Helper Object
                var promotionBuilder = {
                    showMessage: function (title, message) {
                        var modal = document.querySelector(`#promotion_builder_modal_${event.id}`);
                        modal.querySelector('.promotion-builder-modal-title').innerText = title;
                        modal.querySelector('.promotion-builder-modal-message').innerText = message;

                        window.MicroModal.show(`promotion_builder_modal_${event.id}`, {
                            disableScroll: true,
                            disableFocus: true
                        });
                    }
                };

                var rootWrap = document.createElement('div')
                rootWrap.className = 'promotion-builder-root-wrap';

                var promotionWrap = document.createElement('div')
                promotionWrap.id = `promotion_builder_wrap_${event.id}`;

                event.blocks.forEach(((block, index) => {
                    if (block.blockType === 'IMAGE') {
                        promotionWrap.innerHTML += `<img src="${block.image}" alt="Event Block ${index + 1}" style="display: block; width: 100%;">`;
                    } else if (block.blockType === 'LINK') {
                        promotionWrap.innerHTML += `<a href="${block.url}" target="${block.openType === 'BLANK' ? '_blank' : '_self'}" style="display: block; width: 100%;">
                                                        <img src="${block.image}" alt="Event Block ${index + 1}" style="display: block; width: 100%;">
                                                    </a>`;
                    } else if (block.blockType === 'SCRIPT') {
                        var scriptImgId = `promotion_builder_script_${event.id}_${index + 1}`;
                        document.body.addEventListener('click', function (event) {
                            if (event.target.id === scriptImgId) {
                                eval(block.script);
                            }
                        });

                        promotionWrap.innerHTML += `<img id="${scriptImgId}" src="${block.image}" alt="Event Block ${index + 1}" style="display: block; width: 100%; cursor: pointer;">`;
                    }
                }));

                var modalHtml = `
                    <div id="promotion_builder_modal_${event.id}" class="promotion-builder-modal" aria-hidden="true">
                        <div class="promotion-builder-modal-overlay" tabindex="-1">
                            <div class="promotion-builder-modal-dialog" role="dialog" aria-modal="true">
                                <div class="promotion-builder-modal-header">
                                    <div style="display: flex; justify-content: end;">
                                        <button type="button" class="promotion-builder-modal-close" aria-label="Close modal" data-micromodal-close>&#x2715;</button>
                                    </div>
                                    <span class="promotion-builder-modal-title"></span>
                                </div>
                                <div class="promotion-builder-modal-body">
                                    <span class="promotion-builder-modal-message"></span>
                                </div>
                                <div class="promotion-builder-modal-footer">
                                    <button type="button" class="promotion-builder-modal-confirm" data-micromodal-close>확인</button>
                                </div>
                            </div>
                        </div>
                    </div>

                    <style>
                        #promotion_builder_modal_${event.id} {
                            position: relative;
                            display: none;
                            font-style: normal;
                            font-size: 16px;
                            z-index: 10000;
                        }

                        #promotion_builder_modal_${event.id}.is-open {
                            display: block;
                        }

                        #promotion_builder_modal_${event.id} .promotion-builder-modal-overlay {
                            position: fixed;
                            top: 0;
                            bottom: 0;
                            left: 0;
                            right: 0;
                            display: flex;
                            justify-content: center;
                            align-items: center;
                            background-color: rgba(0, 0, 0, 0.3);
                        }

                        #promotion_builder_modal_${event.id} .promotion-builder-modal-dialog {
                            position: relative;
                            width: 100%;
                            max-width: 460px;
                            max-height: 100vh;
                            margin: 0 auto;
                            border: 1px solid #dbdbdb;
                            border-radius: 10px;
                            background-color: #ffffff;
                            box-shadow: 3px 5px 8px rgba(0, 0, 0, 0.1);
                            box-sizing: border-box;
                            overflow-y: auto;
                        }

                        #promotion_builder_modal_${event.id} .promotion-builder-modal-header {
                            padding: 24px 24px 0;
                        }

                        #promotion_builder_modal_${event.id} .promotion-builder-modal-body {
                            padding: 30px 24px 0;
                        }

                        #promotion_builder_modal_${event.id} .promotion-builder-modal-footer {
                            padding: 30px 24px 24px;
                        }

                        #promotion_builder_modal_${event.id} .promotion-builder-modal-close {
                            background-color: transparent;
                            margin: 0;
                            padding: 0;
                            border: 0;
                            cursor: pointer;
                            font-size: 20px;
                        }

                        #promotion_builder_modal_${event.id} .promotion-builder-modal-title {
                            font-size: 20px;
                            font-weight: bold;
                        }

                        #promotion_builder_modal_${event.id} .promotion-builder-modal-confirm {
                            display: block;
                            width: 100%;
                            cursor: pointer;
                            background-color: #0d6efd;
                            border: solid 1px #0d6efd;
                            border-radius: 10px;
                            color: #fff;
                            font-weight: bold;
                            font-size: 16px;
                            padding: 12px;
                        }
                    </style>
                `;

                rootWrap.appendChild(promotionWrap);
                el.parentNode.insertBefore(rootWrap, el);

                document.body.innerHTML += modalHtml;
            });
    }
}());
