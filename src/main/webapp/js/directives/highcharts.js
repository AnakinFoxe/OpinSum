var highchartDirective = angular.module('highcharts',[]);

highchartDirective.directive("highcharts", ['$sce','$timeout',function($sce, $timeout){
    var setTheme = function(){
        var theme={colors:["#f45b5b","#8085e9","#8d4654","#7798BF","#aaeeee","#ff0066","#eeaaee","#55BF3B","#DF5353","#7798BF","#aaeeee"],chart:{backgroundColor:"white",style:{fontFamily:"Dosis"}},title:{style:{color:"black",fontSize:"30px",fontWeight:"bold"}},subtitle:{style:{color:"black"}},tooltip:{borderWidth:0},legend:{itemStyle:{fontWeight:"bold",fontSize:"13px"}},xAxis:{labels:{style:{fontSize:"18px",color:"#6e6e70"}}},yAxis:{labels:{style:{fontSize:"18px",color:"#6e6e70"}}},plotOptions:{series:{shadow:true},candlestick:{lineColor:"#404048"},map:{shadow:false}},navigator:{xAxis:{gridLineColor:"#D0D0D8"}},rangeSelector:{buttonTheme:{fill:"white",stroke:"#C0C0C8","stroke-width":1,states:{select:{fill:"#D0D0D8"}}}},scrollbar:{trackBorderColor:"#C0C0C8"},background2:"#E0E0E8"}
        // Apply the theme
        Highcharts.setOptions(theme);
    }
    //this method format raw data string into a list
    var formatSummaries = function(string){
        var htmlString = "";
        htmlString += '<li><h4><u>Reviews : </u></h4></li>'
        for(var i = 0; i < string.length; i++){
            htmlString += '<li><i class="fa fa-angle-double-right"></i> ' + string[i].replace(/"/g) + "</li>";
        }
        return htmlString;
    }
    //this method convert raw data into highcharts data format
    var highchartsData = function(phone, scope){
        var aspectCatergories = [], negSentiment = [], posSentiment = [];

        angular.forEach(phone, function(value, key) {
            aspectCatergories.push(value.name);

            //parsing positive and negative sentiment from nested array
            //ex: "sentimentCounts":[1,2],"sentiment":[33,67]
            posSentiment.push({
                y : value.sentiment[0],
                data : value.sentimentCounts[0],
                summaries: value.summaries[0]
            });
            negSentiment.push({
                y : value.sentiment[1],
                data : value.sentimentCounts[1],
                summaries: value.summaries[1]
            });
        });

        var data = {
            chart :{
                type: 'bar',
                animation: false,
                renderTo: 'chart'
            },
            title: {
                text: 'Aspect List'
            },
            xAxis: {
                categories: aspectCatergories
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
            subtitle: {
                style: {
                    fontSize: '14px',
                    fontFamily: 'Dosis'
                },
                text: '* Hover on chart will display review counts <br> * Click on chart will display review summaries',
                align: 'right',
                x: -10
            },
            plotOptions: {
                series: {
                    stacking: 'normal',
                    point: {
                    events: {
                        click: function () {
                                scope.description = formatSummaries(this.summaries);
                                scope.$apply();
                            }
                        }
                    }
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
            series:[{
                name:'negative reviews',
                data: negSentiment,
                pointWidth: 23
            },{
                    name: 'positive reviews',
                    data: posSentiment,
                    pointWidth: 23
                }
            ]
        }
        return data;
    }
    return{
        restrict: 'AE',
        replace: false,
        template: '<div id="chart"></div>',
        scope: {
            data : '='
        },
        link: function(scope, elem, attrs){
            scope.$watch('data', function(data){
                if(data){
                    setTheme();
                    var chart = new Highcharts.Chart(highchartsData(data, scope));
                }
            });
            scope.$watch('description', function(data){
                if(data){
                    scope.$parent.setDescription(data);
                }
            });
        }
    }
}]);