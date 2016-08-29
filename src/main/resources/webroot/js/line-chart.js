$(document).ready(function() {

$( function() {
    $( "#datepicker" ).datepicker();
  } );

   $('#searchForm').validate({ // initialize the plugin
          rules: {
              dataPoint: {
                  required: true,
                  number: true
              }
          }
      });

    $('#searchForm input').on('keyup blur', function () { // fires on every keyup & blur
          if ($('#searchForm').valid()) {                   // checks form for validity
              $('submit-button').prop('disabled', false);        // enables button
          } else {
              $('submit-button').prop('disabled', 'disabled');   // disables button
          }
      });


$( "#searchForm" ).submit(function( event ) {

  var $form = $( this ),
    term = $("#searchForm").serialize()
    url = $form.attr( "action" );

  console.log("valid: " + $('#searchForm').valid());
  if($('#searchForm').valid()) {
    var posting = $.post( url, { term } );
    event.preventDefault();
    location.reload();
  }
  // Put the results in a div
  posting.done(function( data ) {
    var content = $( data ).find( "#content" );
    $( "#result" ).empty().append( content );
  });

});

    $.ajax({
        url: 'http://localhost:8080/line-chart?name=line-chart-1&after=2016-08-01&before=2016-08-31',
        success: function(response) {
            var graphPoints = [];
            var timeStamps = [];
            for(var i in response.data) {
              var timestamp = JSON.stringify(response.data[i].timestamp);

              var d = new Date(0)
              d.setUTCSeconds(timestamp);
              timeStamps.push(formattedDate(d));

              var dataPoint = parseFloat(JSON.stringify(parseFloat(response.data[i].dataPoint)));
              graphPoints.push(dataPoint);
            }
            makeHighChart(timeStamps,graphPoints);
        },
        cache: false
    });
});

function formattedDate(date) {
  var year = date.getFullYear();
  var month = (1 + date.getMonth()).toString();
  month = month.length > 1 ? month : '0' + month;
  var day = date.getDate().toString();
  day = day.length > 1 ? day : '0' + day;
  return month + '/' + day + '/' + year;
}

// render graph
function makeHighChart(timeStamps,dataPoints) {
    console.log("data points: " + dataPoints);
    $('#container').highcharts({
        chart: {
            type: 'line'
        },
        title: {
            text: 'Monthly Average Temperature'
        },
        subtitle: {
            text: 'Source: WorldClimate.com'
        },
        xAxis: {
            categories: timeStamps
        },
        yAxis: {
            title: {
                text: 'Temperature (°C)'
            }
        },
        plotOptions: {
            line: {
                dataLabels: {
                    enabled: true
                },
                enableMouseTracking: false
            }
        },
        series: [{
            name: 'Tokyo',
            data: dataPoints
        }]
    });
}