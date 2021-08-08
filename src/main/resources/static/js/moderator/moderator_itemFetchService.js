$(async function () {
    await getUnmoderatedItems();
})

const url = "http://" + window.location.hostname + ":8888/moderator/api";
const itemFetchService = {
    head: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'Referer': null
    },
    getUnmoderatedItems: async () => await fetch(`${url}/getUnmoderatedItems`),
    getOneUnmoderatedItem: async (id) => await fetch(`${url}/getOneUnmoderatedItem/${id}`),
    updateItem: async (item) => await fetch(`${url}/editItem`, {
        method: 'PUT',
        headers: itemFetchService.head,
        body: JSON.stringify(item)
    }),
    getUnmoderatedItemsCounter: async () => await fetch(`${url}/getUnmoderatedItemsCount`)
}

async function getUnmoderatedItems() {
    let itemCards = $('#itemsCards')
    await itemFetchService.getUnmoderatedItems()
        .then(res => res.json())
        .then(items => {
                if (items.length === 0) {
                    itemCards.append(`<div class="row">
                         <div class="col-sm-6">
                            <div class="card" id="emptyItemList">
                                <div class="row card-body">
                                    <div class="col-sm-6">
                          <h5 class="card-title"><b>Все товары отмодерированы!</h5>
                           <p class="card-text" id="cardText"><b>Больше товаров нет, но есть замечательный кот!<br>
                        
                            </p>
                  
                        </div>
                        <img class="col-sm-6 text-right" src="https://w-dog.ru/wallpapers/5/19/361416555427750/kot-kotenok-seryj-pushistyj-lapy-kogotki.jpg" alt="https://iproger.ru/content/news/46014.jpg"/>
                    </div>
                </div>
                </div>
                </div>
                <br>`)
                } else {
                    items.forEach(item => {
                        let cardFilling =
                            `<div class="row">
                         <div class="col-sm-6">
                            <div class="card" id="itemCard${item.id}">
                                <div class="row card-body">
                                    <div class="col-sm-6">
                          <h5 class="card-title"><b>Наименование товара: </b>${item.name}</h5>
                           <p class="card-text" id="cardText"><b>Описание товара: </b>${item.description}<br>
                            <b>Категории товара: </b>${item.categories}<br>
                            </p>
                        
                           <button data-itemid="${item.id}" data-action="accept" class="btn btn-success">Одобрить</a>
                           <button data-itemid="${item.id}" data-action="decline" class="btn btn-danger">Отклонить</button>
                          
                        </div>
                        <img class="col-sm-6 text-right" src="${item.images[0]}" alt="https://iproger.ru/content/news/46014.jpg"/>
                    </div>
                </div>
                </div>
                </div>
                <br>`
                        itemCards.append(cardFilling);
                    })
                    getUnmoderatedItemsCountAndSetBadge();
                }
            }
        )
    $("#itemsCards").find('button').on('click', (event) => {
        let targetButton = $(event.target);
        let buttonItemId = targetButton.attr('data-itemid');
        let buttonAction = targetButton.attr('data-action');
        if (buttonAction === "accept") {
            acceptItem(buttonItemId);
        } else if (buttonAction === "decline") {
            declineItem(buttonItemId);
        }
    })
}

async function acceptItem(id) {
    await itemFetchService.getOneUnmoderatedItem(id)
        .then(res => res.json())
        .then(async item => {
            item.moderated = true;
            item.moderateAccept = true;
            const response = await itemFetchService.updateItem(item);
            if (response.ok) {
                let itemCards = $('#itemsCards')
                itemCards.find(`#itemCard${item.id}`).remove()
                await itemFetchService.getUnmoderatedItems()
                    .then(res => res.json())
                    .then(items => {
                        if (items.length === 0) {
                            itemCards.append(`<div class="row">
                         <div class="col-sm-6">
                            <div class="card" id="emptyItemList">
                                <div class="row card-body">
                                    <div class="col-sm-6">
                          <h5 class="card-title"><b>Все товары отмодерированы!</h5>
                           <p class="card-text" id="cardText"><b>Больше товаров нет, но есть замечательный кот!<br>
                        
                            </p>
                  
                        </div>
                        <img class="col-sm-6 text-right" src="https://w-dog.ru/wallpapers/5/19/361416555427750/kot-kotenok-seryj-pushistyj-lapy-kogotki.jpg" alt="https://iproger.ru/content/news/46014.jpg"/>
                    </div>
                </div>
                </div>
                </div>
                <br>`)
                        }
                    })
                await getUnmoderatedItemsCountAndSetBadge();
            } else {
                console.log("Произошла ошибка")
            }
        })
}

async function declineItem(id) {
    let form;
    let cardField;
    if (!(document.getElementById(`declineItem${id}`))) {
        await itemFetchService.getOneUnmoderatedItem(id)
            .then(res => res.json())
            .then(item => {
                    if (!(document.getElementById(`declineItem${item.id}`))) {
                        form = `<form id="declineItem${item.id}">
                    <input type="text" class="form-control" id="rejectedReasonTo${item.id}" name="rejectedReasonTo${item.id}" placeholder="Введите причину отказа" style="width: 200px; height: 150px;" 
                    min="5">
                    <button type="button" class="btn btn-primary" id="rejectReason${item.id}">Отправить</button>\
                    </form>`
                        cardField = $("#itemsCards").find(`#itemCard${item.id}`);
                        cardField.append(form);
                        console.log("Createdd form")
                    }
                    $(`#declineItem${item.id}`).find('button').on('click', () => {
                        item.moderated = true;
                        item.moderateAccept = false;
                        item.moderatedRejectReason = document.querySelector(`#rejectedReasonTo${item.id}`).value;
                        sendItemAndDeleteCard(item);
                    })
                }
            )
    }
}

async function sendItemAndDeleteCard(item) {
    const response = await itemFetchService.updateItem(item);
    if (response.ok) {
        let itemCards = $('#itemsCards')
        itemCards.find(`#itemCard${item.id}`).remove()
        await itemFetchService.getUnmoderatedItems()
            .then(res => res.json())
            .then(items => {
                if (items.length === 0) {
                    itemCards.append(`<div class="row">
                         <div class="col-sm-6">
                            <div class="card" id="emptyItemList">
                                <div class="row card-body">
                                    <div class="col-sm-6">
                          <h5 class="card-title"><b>Все товары отмодерированы!</h5>
                           <p class="card-text" id="cardText"><b>Больше товаров нет, но есть замечательный кот!<br>
                        
                            </p>
                  
                        </div>
                        <img class="col-sm-6 text-right" src="https://w-dog.ru/wallpapers/5/19/361416555427750/kot-kotenok-seryj-pushistyj-lapy-kogotki.jpg" alt="https://iproger.ru/content/news/46014.jpg"/>
                    </div>
                </div>
                </div>
                </div>
                <br>`)
                }
                getUnmoderatedItemsCountAndSetBadge();
            })
    } else {
        console.log("Произошла ошибка")
    }
}

async function getUnmoderatedItemsCountAndSetBadge() {
    let precounter = await itemFetchService.getUnmoderatedItemsCounter();
    let counter = precounter.json();
    counter.then(counter => {
        $("#v-pills-tab #v-pills-items-tab").html(`Товары <span
                        class="badge badge-light">${counter}</span>
                    <span class="sr-only">unmoderated items</span></a>`)
    })
}
            
