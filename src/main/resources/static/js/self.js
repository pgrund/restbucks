selectedOrder = {
    location: "IN_SHOP",
    items: [
        {
            quantity: "",
            name: "",
            milk: "",
            size: "",
            shots: ""
        }
    ],
    "_links": {
        "self": {
            "href": "/orders"
        }
    }
};

function updateHTML(order, orderEle) {
    
    function linksUpdate(order, orderEle) {
        $(orderEle).find('.links ul.nav-tabs li').addClass('disabled');
        $(orderEle).find('.links ul.nav-tabs li a').attr("data-toggle", "disabled-tab");
        
        $(orderEle).find('.links .tab-content .status').hide();

        $.each(order._links, (function (rel, obj) {
            var linkRel = rel.split(':').pop();
            $('#link_' + linkRel).removeClass('disabled');
            $('#link_' + linkRel + ' a').attr("data-toggle", "tab");
            // add url
            $('#' + linkRel).find('button').data('href', (linkRel === 'curies' ? obj : obj.href));
            if (linkRel === 'self') {
                $('#self button').data('href', obj.href);
                if (!order.hasOwnProperty('status')) {
                    $("#self .status.new").show();
                } else if (order.status === 'UNPAID') {
                    $("#self p.status.self").val('Your order is awaiting payment. <br/> You may also <a href="#cancel" data-toggle="tab">cancel</a> your order completely.</p>');
                    $("#self .status.unpaid").show();
                } else {
                    $("#self p.status.self").html('<p>Your order is queued .... </p>');
                    $("#self .status.self").show();
                }
            }
        }));
    }
    function orderUpdate(order, orderEle) {

        selectedOrder = JSON.parse(JSON.stringify(order));
        
        delete selectedOrder._links;

        // fill form
        $(orderEle).find('.orderid').text(": " + order.id);
        $(orderEle).find("input[name=location][value=" + order.location + "]")
                .prop('checked', true);
        $(orderEle).find("input[name=location]").on('change', function () {
            selectedOrder.location = $(this).val();
        });

        $(orderEle).find('.items select').val("");
        $(orderEle).find('.items input').val("");
        order.items.forEach(function (item, idx) {
            if (!$('#' + idx + '_quantity').length) {
                newItem(idx);
            }
            $('#' + idx + '_quantity').val(item.quantity).on('change', function () {
                selectedOrder.items[idx].quantity = parseInt($(this).val());
            });
            $('#' + idx + '_name').val(item.name).on('change', function () {
                selectedOrder.items[idx].name = $(this).val();
            });
            $('#' + idx + '_milk').val(item.milk).on('change', function () {
                selectedOrder.items[idx].milk = $(this).val();
            });
            $('#' + idx + '_size').val(item.size).on('change', function () {
                selectedOrder.items[idx].size = $(this).val();
            });
            $('#' + idx + '_shots').val(item.shots).on('change', function () {
                selectedOrder.items[idx].shots = $(this).val();
            });
        });
    }

    linksUpdate(order, orderEle);
    orderUpdate(order, orderEle);
}
function self(url, orderEle) {
    $.getJSON(url, function (order) {
        updateHTML(order, orderEle);        
    });
}

function cancel(buttonEle) {
    var url = $(buttonEle).data("href");
    var orderEle = $(buttonEle).parents().find('.jumbotron')[0];

    $.ajax({
        url: url,
        type: 'DELETE'
    }).done(function () {
        messagebox("Success", 'order has been canceled', "success");
        self(url, orderEle);
    });
    return false;
}

function update(buttonEle) {
    var url = $(buttonEle).data("href");
    var orderEle = $(buttonEle).parents().find('.jumbotron')[0];

    $.ajax({
        url: url,
        type: 'PUT',
        contentType: 'application/json',
        data: JSON.stringify(selectedOrder)
    }).done(function (updatedOrder) {
        messagebox("Success", 'resource updated ', "success");
        self(url, orderEle);
    });
    return false;
}
function create(buttonEle) {
    var url = $(buttonEle).data("href");
    // no orderid
    delete selectedOrder.id;

    $.ajax({
        url: url,
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(selectedOrder)
    }).done(function (updatedOrder) {
        messagebox("Success", 'resource updated ... redirect in 5sec', "success");
        window.location = '#';
        window.setTimeout('window.location = "' + updatedOrder._links.self.href + '.html"', 5000);
    });
    return false;

}

