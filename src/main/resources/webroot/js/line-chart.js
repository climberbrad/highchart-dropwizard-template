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
            $('#submit-button').prop('disabled', false);        // enables button
        } else {
            $('#submit-button').prop('disabled', 'disabled');   // disables button
        }
    });

    $( "#searchForm" ).submit(function( event ) {
      var $form = $( this ),
        term = $("#searchForm").serialize()
        url = $form.attr("action") + "/" + graphName;

      if( $('#searchForm').valid() ) {
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
        url: 'http://localhost:8080/line-chart?name=Sample%20Line%20Chart&after=' + minusMonth() + '&before=' + now(),
        success: function(response) {
            graphName=response.chartName;
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
            makeHighChart(graphName,timeStamps,graphPoints);
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

function now() {
  var d = new Date();
  var month = d.getMonth()+1;
  var day = d.getDate();

  var output = d.getFullYear() + "-"
              + (month<10 ? '0' : '') + month + '-'
              + (day<10 ? '0' : '') + day;

  return output;
}

function minusMonth() {
  var d = new Date();
  var month = d.getMonth();
  var day = d.getDate();

  var output = d.getFullYear() + "-"
              + (month<10 ? '0' : '') + month + '-'
              + (day<10 ? '0' : '') + day;

  return output;
}

// render graph
function makeHighChart(graphName,timeStamps,dataPoints) {
    $('#container').highcharts({
        chart: {
            type: 'line'
        },
        title: {
            text: graphName
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