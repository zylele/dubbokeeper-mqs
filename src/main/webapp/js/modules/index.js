var dubbokeeper=angular.module("dubbokeeper",["apps","head","menu","breadCrumb",'aboutus','fullScreen',"tracking","alarm",'dialog','statistics','monitor','isteven-multi-select','appDependencies','zoopeeper','httpWrapper','override','router','theme','lineChart','dateRangePicker',"alarmset"]);
dubbokeeper.controller("dubbokeeperCtrl",function($scope,$dkContext,$rootScope){
    $dkContext._init($scope);
    $rootScope.warnStatus = {"bizStatus":false,"serviceStatus":false};
    $scope.currentHome={};
});
dubbokeeper.barDataset=[{
    barShowName:"控制面板",
    barIdentify:"dashboard",
    barHref:"/statistics",
    barIconClass:"icon-dashboard"
},{
    barShowName:"管理",
    barIdentify:"admin",
    barHref:"/admin/apps",
    barIconClass:"glyphicon glyphicon-user",
    menus:[{
        showName:"应用列表",
        identify:"admin/apps",
        href:"/admin/apps",
        icon:"icon-cloud",
        isHome:true
    },{
        showName:"服务列表",
        identify:"admin/services",
        href:"/admin/services",
        icon:"icon-share",
    },{
        showName:"动态配置",
        identify:"admin/dynamicConfig",
        href:"/admin/override/list",
        icon:"icon-cogs"
    },{
        showName:"路由规则",
        identify:"admin/routeConfig",
        href:"/admin/route/list",
        icon:"icon-random"
    }]
},{
    barShowName:"监控",
    barIdentify:"monitor",
    barHref:"/monitor",
    barIconClass:"icon-bar-chart",
    menus:[{
        showName:"监控室",
        identify:"monitor/index",
        href:"/monitor",
        icon:"icon-bar-chart",
        disable:true,
        isHome:true
    }]
},{
    barShowName:"Zoopeeper监控",
    barIdentify:"zoopeeper",
    barHref:"/zoopeeper",
    barIconClass:"icon-eye-open"
},{
    barShowName:"关于我们",
    barIdentify:"aboutus",
    barHref:"/aboutus",
    barIconClass:"icon-bookmark"
},{
    barShowName:"全链路跟踪",
    barIdentify:"tracking",
    barHref:"/tracking/text",
    barIconClass:"icon-screenshot"
},{
    barShowName:"故障告警",
    barIdentify:"alarm",
    barHref:"/alarm/alarmlist",
    barIconClass:"icon-warning-sign",
    menus:[{
        showName:"服务异常",
        identify:"alarm/alarmlist",
        href:"/alarm/alarmlist",
        icon:"icon-th-large",
        isHome:true
    },{
    	showName:"业务异常",
        identify:"alarm/alarmBusiness",
        href:"/alarm/alarmBusiness",
        icon:"icon-th-large"
    },{
        showName:"通知设置",
        identify:"alarm/alarmSet",
        href:"/alarm/alarmSet",
        icon:" icon-cogs"
    }]
}];
dubbokeeper.$dkContext= function () {
    var dubboKeeperContext = function () {
        this.inited=false;
    }
    dubboKeeperContext.prototype._init=function($scope){
        this.scope = $scope;
        if(this.temporaryContainer){
            for(var i=0;i<this.temporaryContainer.length;i++){
                this.scope[this.temporaryContainer[i].key]=this.temporaryContainer[i].value;
            }
        }
        this.inited=true;
    }
    dubboKeeperContext.prototype.changeProperty=function(key,value){
        if(this.inited){
            this.scope[key] = value;
        }else{
            if(this.temporaryContainer){
                this.temporaryContainer=[];
            }
            this.temporaryContainer.push({key:key,value:value});
        }
    }
    dubboKeeperContext.prototype.getProperty=function(key){
        if(this.scope){
            return this.scope[key];
        }
    }
    dubboKeeperContext.prototype.getBars=function(){
        return dubbokeeper.barDataset;
    }
    dubboKeeperContext.prototype.getBarByMenuIdentify=function(menuIdentify){
        for(var i=0;i<dubbokeeper.barDataset.length;i++){
            var bar = dubbokeeper.barDataset[i];
            if(bar.menus){
                for(var j=0;j<bar.menus.length;j++){
                    if(bar.menus[j].identify==menuIdentify){
                        return bar;
                    }
                }
            }
        }
    }
    this.$get = function () {
        return new dubboKeeperContext();
    };
}
dubbokeeper.provider("$dkContext",dubbokeeper.$dkContext);
