define(['jquery','highcharts'],function($,highcharts){

	var renderChart = function(data){
		//initilize font, background, font
		theme = renderChart.init();
		Highcharts.setOptions(theme);

		var $chart = $("#chart");
		var arr = [], pos = [], neg = [];
		$.each(data, function(i,val){
			arr.push(val.title);
			pos.push({
				y : val.posCount,
				data : val.posCount
				// data: val.posComt
			});
			neg.push({
				y: val.negCount,
				data : val.negCount
				//data: val.negComt
			});
		});
		$chart.highcharts({
			chart: {
				type: 'bar',
				// borderRadius : '10px',
				// borderColor: 'gray',
				// borderWidth: '5px'
			},
			title: {
            	text: 'Aspect List'
	        },
	        xAxis: {
	            categories: arr
	        },
	        yAxis: {
	            min: 0,
	            max: 100,
	            title: {
	                text: 'Total Percentage'
	            }
	        },
	        legend: {
	            reversed: true
	        },
	        plotOptions: {
	            series: {
	                stacking: 'normal'
	            }
	        },
	        tooltip: {
	        	useHTML: true,
	        	followTouchMove: true,
	        	followPointer: true,
	        	formatter: function() {
               		return this.point.data;
     			}
	        },
	        series: [{
	            name:'positive',
	            data: pos
	        }, {
	            name: 'negative',
	            data: neg
	        }]
		});

	}

	renderChart.init = function(){
		
		/**
 		* Sand-Signika theme for Highcharts JS
 		* @author Torstein Honsi
 		*/

    	//Load the fonts
		Highcharts.createElement('link', {
		   href: 'http://fonts.googleapis.com/css?family=Signika:400,700',
		   rel: 'stylesheet',
		   type: 'text/css'
		}, null, document.getElementsByTagName('head')[0]);

		// Add the background image to the container
		// Highcharts.wrap(Highcharts.Chart.prototype, 'getContainer', function (proceed) {
		//    proceed.call(this);
		//    this.container.style.background = 'url(http://www.highcharts.com/samples/graphics/sand.png)';
		// });

		theme = {
		   colors: ["#f45b5b", "#8085e9", "#8d4654", "#7798BF", "#aaeeee", "#ff0066", "#eeaaee",
		      "#55BF3B", "#DF5353", "#7798BF", "#aaeeee"],
		   chart: {
		      backgroundColor: null,
		      style: {
		         fontFamily: "Signika, serif"
		      }
		   },
		   title: {
		      style: {
		         color: 'black',
		         fontSize: '20px',
		         fontWeight: 'bold'
		      }
		   },
		   subtitle: {
		      style: {
		         color: 'black'
		      }
		   },
		   tooltip: {
		      borderWidth: 0
		   },
		   legend: {
		      itemStyle: {
		         fontWeight: 'bold',
		         fontSize: '13px'
		      }
		   },
		   xAxis: {
		      labels: {
		         style: {
		         	fontSize: '18px',
		            color: '#6e6e70'
		         }
		      }
		   },
		   yAxis: {
		      labels: {
		         style: {
		         	fontSize: '18px',
		            color: '#6e6e70'
		         }
		      }
		   },
		   plotOptions: {
		      series: {
		         shadow: true
		      },
		      candlestick: {
		         lineColor: '#404048'
		      },
		      map: {
		         shadow: false
		      }
		   },

		   // Highstock specific
		   navigator: {
		      xAxis: {
		         gridLineColor: '#D0D0D8'
		      }
		   },
		   rangeSelector: {
		      buttonTheme: {
		         fill: 'white',
		         stroke: '#C0C0C8',
		         'stroke-width': 1,
		         states: {
		            select: {
		               fill: '#D0D0D8'
		            }
		         }
		      }
		   },
		   scrollbar: {
		      trackBorderColor: '#C0C0C8'
		   },

		   // General
		   background2: '#E0E0E8'
		   
		};

		// Apply the theme
		return theme;
	}

	return renderChart;
});