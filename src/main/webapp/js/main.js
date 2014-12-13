require.config({
    baseUrl: '/js',
    paths: {
        // the left side is the module ID,
        // the right side is the path to
        // the jQuery file, relative to baseUrl.
        // Also, the path should NOT include
        // the '.js' file extension. This example
        // is using jQuery 1.9.0 located at
        // js/lib/jquery-1.9.0.js, relative to
        // the HTML page.
        domReady: '../lib/domReady/domReady',
        jquery: '../lib/jquery/dist/jquery.min',
        bootstrap: '../lib/bootstrap/js/bootstrap.min',
        canvasjs: '../lib/canvasjs.min',
        highcharts: '../lib/highcharts',
        exportHighCharts: '../lib/exporting',
        spinner : '../lib/spinner'
    },
    shim : {
        "bootstrap" : { "deps" :['jquery'] }
    }
});


