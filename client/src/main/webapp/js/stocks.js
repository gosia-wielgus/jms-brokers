$(document).ready(function(){
	reload();
	window.setInterval(reload, 1000);
});

function getPercentChange(value, change) {
	return '('+(change/value*100).toFixed(2) + '%)'
}
function getAbsoluteChange(change) {
	var result = change
	if (result > 0)
		result = '+' + result
	return result
}

var stocks = {};


function reload() {
$.getJSON('data.json', function(data) {
  var items = [];
  items.push('<tr><th>index name</th><th>value</th><th>change</th><th>percent</th></tr>');

  var stock_indices = []
  $.each(data.stock_indices, function(k, v) { stock_indices.push(v)})
  stock_indices.sort(function (a, b){ return (a > b) - (a < b)})
  for (var i in stock_indices) {
	  var val = stock_indices[i];
	  var change_class = '';
	  if (val.change > 0) 
		  change_class = 'stock-change-positive'
	  else if (val.change < 0)
		  change_class = 'stock-change-negative'
	  else
		  change_class = 'stock-change-zero'
    items.push('<tr>'+
    		'<td class="stock-name">'+val.name+'</td>'+
    		'<td class="stock-value">'+val.value+'</td>'+
    		'<td class="'+change_class+' stock-change">'+getAbsoluteChange(val.change)+'</td>'+
    		'<td class="'+change_class+' stock-change">'+getPercentChange(val.value, val.change)+'</td>'+
    		'</tr>');
  }

  var table = $('<table/>', {
    'class': 'stock-index-table',
    html: items.join('')
  });
  $('.stock-index-table').replaceWith(table);
  /*$('.stock-change-positive').animate({
	  color:'#00FF00'
  }, 100);
  $('.stock-change-negative').animate({
	  color:'#FF0000'
  }, 100);*/
});
}
