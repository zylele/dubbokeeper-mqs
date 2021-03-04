angular.module('mePagination',[])
    .factory('myPage',function () {
                var f={};
                f.pageNub=1;
                f.getPageNub=function () {
                    return f.pageNub;
                };
                f.setPageNub=function (n) {
                    f.pageNub=n;
                };
                return f;
            })
    .directive('myPagination',['myPage',function (myPage) {
                return {
                    restrict:'EA',
                    replace:true,
                    template:'<div class="my-page">'+
                            '<div class="page">'+
                                 '<span class="page-list" ng-class="{disabled: pg.currentPage == 1}" ng-click="prevPage()">&#171;</span>'+
                                 '<span class="page-list" ng-class="{active:item==pg.currentPage,borderNone:item==\'...\'}" ng-click="changeCurrentPage(item)" ng-repeat="item in pageList track by $index">{{item}}</span>'+
                                 '<span class="page-list" ng-class="{disabled: pg.currentPage == pg.numberOfPages}" ng-click="nextPage()">&#187;</span>'+
                             '</div>'+
                             '<div class="goPage">'+
                                 '<span>每页<select name="" id="" ng-model="pg.itemsPerPage" ng-options="option for option in pg.perPageOptions " ng-change="changeItemsPerPage()"></select>条/</span>'+
                                 '<span>共{{pg.totalItems}}条</span>'+
                             '</div>'+
                             '<div ng-if="pg.showError">请输入正确页码</div>'+
                         '</div>',
                    scope:{
                        pg:'='
                    },
                    link: function(scope, element, attrs){
                         // ng-show="pg.totalItems > pg.itemsPerPage"
                        // 变更当前页
                        scope.pg.currentPage=myPage.pageNub;
                        scope.changeCurrentPage = function(p){
                            if(p == '...'){
                                return;
                            }else{
                                scope.pg.currentPage = p;
                                myPage.setPageNub(scope.pg.currentPage);
                            }
                            getPagination();
                        };
                        scope.prevPage = function(){
                            if(scope.pg.currentPage > 1){
                                scope.pg.currentPage -= 1;
                            }else {
                                scope.pg.currentPage=1;
                            }
                            myPage.setPageNub(scope.pg.currentPage);
                            getPagination();
                        };
                        // nextPage
                        scope.nextPage = function(){
                            if(scope.pg.currentPage < scope.pg.numberOfPages){
                                scope.pg.currentPage += 1;
                            }else {
                                scope.pg.currentPage=scope.pg.numberOfPages;
                            }
                            myPage.setPageNub(scope.pg.currentPage);
                            getPagination()
                        };

                        // 跳转页
                        scope.jumpToPage = function(){
                                if(scope.pg.jumpPageNum>0 || scope.pg.jumpPageNum<=scope.pg.numberOfPages){
                                    scope.pg.currentPage=scope.pg.jumpPageNum;
                                    myPage.setPageNub(scope.pg.currentPage);
                                    getPagination();
                                    scope.pg.jumpPageNum='';
                                }else {
                                    scope.pg.showError=true;
                                }
                        };

                        // 修改每页显示的条数
                        scope.changeItemsPerPage = function(){
                            getPagination();
                        };

                        // 定义分页的长度必须为奇数 (default:9)
                        scope.pg.pagesLength = parseInt(scope.pg.pagesLength) ? parseInt(scope.pg.pagesLength) : 9 ;
                        if(scope.pg.pagesLength % 2 === 0){
                            // 如果不是奇数的时候处理一下
                            scope.pg.pagesLength = scope.pg.pagesLength -1;
                        }

                        // pg.erPageOptions
                        if(!scope.pg.perPageOptions){
                            scope.pg.perPageOptions = [10, 15, 20, 30, 50];
                        }

                        // pageList数组
                        getPagination();
                        function getPagination(){
                            // pg.currentPage
                            scope.pg.currentPage = parseInt(myPage.pageNub);
                            // pg.totalItems
                            scope.pg.totalItems = parseInt(scope.pg.totalItems);

                            // numberOfPages,总共分多少页
                            scope.pg.numberOfPages = Math.ceil(scope.pg.totalItems/scope.pg.itemsPerPage);

                            // judge currentPage > scope.numberOfPages
                            if(scope.pg.currentPage < 1){
                                scope.pg.currentPage = 1;
                            }

                            if(scope.pg.currentPage > scope.pg.numberOfPages){
                                scope.pg.currentPage = scope.pg.numberOfPages;
                            }


                            scope.pageList = [];
                            if(scope.pg.numberOfPages <= scope.pg.pagesLength){
                                // 判断总页数如果小于等于分页的长度，若小于则直接显示
                                for(i =1; i <= scope.pg.numberOfPages; i++){
                                    scope.pageList.push(i);
                                }
                            }else{
                                // 总页数大于分页长度（此时分为三种情况：1.左边没有...2.右边没有...3.左右都有...）
                                // 计算中心偏移量
                                var offset = (scope.pg.pagesLength - 1)/2;
                                if(scope.pg.currentPage <= offset){
                                    // 左边没有...
                                    for(i =1; i <= offset +1; i++){
                                        scope.pageList.push(i);
                                    }
                                    scope.pageList.push('...');
                                    scope.pageList.push(scope.pg.numberOfPages);
                                    //    >实际总页数-每页的一半
                                }else if(scope.pg.currentPage > scope.pg.numberOfPages - offset){
                                    scope.pageList.push(1);
                                    scope.pageList.push('...');
                                    for(i = offset + 1; i >= 1; i--){
                                        scope.pageList.push(scope.pg.numberOfPages - i);
                                    }
                                    scope.pageList.push(scope.pg.numberOfPages);
                                }else{
                                    // 最后一种情况，两边都有...
                                    scope.pageList.push(1);
                                    scope.pageList.push('...');

                                    for(i = Math.ceil(offset/2) ; i >= 1; i--){
                                        scope.pageList.push(scope.pg.currentPage - i);
                                    }
                                    scope.pageList.push(scope.pg.currentPage);
                                    for(i = 1; i <= offset/2; i++){
                                        scope.pageList.push(scope.pg.currentPage + i);
                                    }

                                    scope.pageList.push('...');
                                    scope.pageList.push(scope.pg.numberOfPages);
                                }
                            }

                        }


//                        scope.$watch(scope.pg.currentPage,function (newClue,oldVlue) {
//                            myPage.setPageNub(scope.pg.currentPage);
//                            console.log('变化啦');
//                            getPagination();
////                            scope.$digest();
//                        });
////                        scope.$watch(scope.pg.totalItems,function (newClue,oldVlue) {
////                            getPagination();
////                        });

                    }
                }
            }])