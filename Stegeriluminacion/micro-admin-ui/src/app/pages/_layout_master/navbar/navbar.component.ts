import { Location } from '@angular/common';
import { HttpErrorResponse } from '@angular/common/http';
import { Component, ElementRef, OnInit, Renderer, ViewChild } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { ActivatedRoute, ActivatedRouteSnapshot, NavigationEnd, Router, UrlSegment } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { Subscription } from 'rxjs/Subscription'; 
import { configuraciones } from '../../../../environments/configuraciones'; 
import { UtilComponent } from '../../../_shared/util.component';
import { ROUTES } from '../sidebar/sidebar.component';
import { LoginService } from 'src/app/_servicios/login.service';
import { Token } from 'src/app/_dto/login/Token.Dto';

const misc: any = {
    navbar_menu_visible: 0,
    active_collapse: true,
    disabled_collapse_init: 0,
};

declare var $: any;
@Component({
    selector: 'app-navbar-cmp',
    templateUrl: 'navbar.component.html'
})

export class NavbarComponent implements OnInit {
    private listTitles: any[];
    location: Location;
    mobile_menu_visible: any = 0;
    private nativeElement: Node;
    private toggleButton: any;

    private sidebarVisible: boolean;
    private _router: Subscription; 

    public paths: any[] = [];
    armar = '/';

    public breadcrumbs: {
        name: string;
        url: string
    }[] = [];
    titulo: string;
    found = false;

    @ViewChild('app-navbar-cmp') button: any;

    constructor(location: Location
        , private renderer: Renderer
        , private element: ElementRef
        , public router: Router
        , public activatedRoute: ActivatedRoute
        , public title: Title
        , public utilComponent: UtilComponent
        , public loginService: LoginService
        , public translate: TranslateService ) {

        this.router.events.subscribe(event => {
            if (event instanceof NavigationEnd) {
                this.breadcrumbs = [];
                this.parseRoute(this.router.routerState.snapshot.root);
            }
        });

        this.location = location;
        this.nativeElement = element.nativeElement;
        this.sidebarVisible = false;
 

    } 
 

    cerrarSesion() {
        this.utilComponent.loading = true;

        let token: Token = <Token>JSON.parse(localStorage.getItem(configuraciones.static.token));
        let logoutDTO = null; //new LogoutDTO();
        logoutDTO.token = token.access_token;
 
    }


    cambiaIdioma(idioma: string) {
        this.translate.setDefaultLang(idioma);
        this.translate.use(idioma);
    }


    private parseRoute(node: ActivatedRouteSnapshot) {
        if (node.data['breadcrumb']) {
            if (node.url.length) {
                let urlSegments: UrlSegment[] = [];
                node.pathFromRoot.forEach(routerState => {
                    urlSegments = urlSegments.concat(routerState.url);
                });
                let url = urlSegments.map(urlSegment => {
                    return urlSegment.path;
                }).join('/');
                this.breadcrumbs.push({
                    name: node.data['breadcrumb'],
                    url: '/' + url
                })
            }
        }
        if (node.firstChild) {
            this.parseRoute(node.firstChild);
        }
    }


    minimizeSidebar() {
        const body = document.getElementsByTagName('body')[0];

        if (misc.sidebar_mini_active === true) {
            body.classList.remove('sidebar-mini');
            misc.sidebar_mini_active = false;

        } else {
            setTimeout(function () {
                body.classList.add('sidebar-mini');

                misc.sidebar_mini_active = true;
            }, 300);
        }

        // we simulate the window Resize so the charts will get updated in realtime.
        const simulateWindowResize = setInterval(function () {
            window.dispatchEvent(new Event('resize'));
        }, 180);

        // we stop the simulation of Window Resize after the animations are completed
        setTimeout(function () {
            clearInterval(simulateWindowResize);
        }, 1000);
    }


    hideSidebar() {
        const body = document.getElementsByTagName('body')[0];
        const sidebar = document.getElementsByClassName('sidebar')[0];

        if (misc.hide_sidebar_active === true) {
            setTimeout(function () {
                body.classList.remove('hide-sidebar');
                misc.hide_sidebar_active = false;
            }, 300);
            setTimeout(function () {
                sidebar.classList.remove('animation');
            }, 600);
            sidebar.classList.add('animation');

        } else {
            setTimeout(function () {
                body.classList.add('hide-sidebar');
                // $('.sidebar').addClass('animation');
                misc.hide_sidebar_active = true;
            }, 300);
        }

        // we simulate the window Resize so the charts will get updated in realtime.
        const simulateWindowResize = setInterval(function () {
            window.dispatchEvent(new Event('resize'));
        }, 180);

        // we stop the simulation of Window Resize after the animations are completed
        setTimeout(function () {
            clearInterval(simulateWindowResize);
        }, 1000);
    }

    ngOnInit() {
        this.listTitles = ROUTES.filter(listTitle => listTitle);

        const navbar: HTMLElement = this.element.nativeElement;
        const body = document.getElementsByTagName('body')[0];
        this.toggleButton = navbar.getElementsByClassName('navbar-toggler')[0];
        if (body.classList.contains('sidebar-mini')) {
            misc.sidebar_mini_active = true;
        }
        if (body.classList.contains('hide-sidebar')) {
            misc.hide_sidebar_active = true;
        }
        this._router = this.router.events.filter(event => event instanceof NavigationEnd).subscribe((event: NavigationEnd) => {
            this.sidebarClose();

            const $layer = document.getElementsByClassName('close-layer')[0];
            if ($layer) {
                $layer.remove();
            }
        });
 
    }



    onResize(event) {
        if ($(window).width() > 991) {
            return false;
        }
        return true;
    }


    sidebarOpen() {
        var $toggle = document.getElementsByClassName('navbar-toggler')[0];
        const toggleButton = this.toggleButton;
        const body = document.getElementsByTagName('body')[0];
        setTimeout(function () {
            toggleButton.classList.add('toggled');
        }, 500);
        body.classList.add('nav-open');
        setTimeout(function () {
            $toggle.classList.add('toggled');
        }, 430);

        var $layer = document.createElement('div');
        $layer.setAttribute('class', 'close-layer');


        if (body.querySelectorAll('.main-panel')) {
            document.getElementsByClassName('main-panel')[0].appendChild($layer);
        } else if (body.classList.contains('off-canvas-sidebar')) {
            document.getElementsByClassName('wrapper-full-page')[0].appendChild($layer);
        }

        setTimeout(function () {
            $layer.classList.add('visible');
        }, 100);

        $layer.onclick = function () { //asign a function
            body.classList.remove('nav-open');
            this.mobile_menu_visible = 0;
            this.sidebarVisible = false;

            $layer.classList.remove('visible');
            setTimeout(function () {
                $layer.remove();
                $toggle.classList.remove('toggled');
            }, 400);
        }.bind(this);

        body.classList.add('nav-open');
        this.mobile_menu_visible = 1;
        this.sidebarVisible = true;
    };
    sidebarClose() {
        var $toggle = document.getElementsByClassName('navbar-toggler')[0];
        const body = document.getElementsByTagName('body')[0];
        this.toggleButton.classList.remove('toggled');
        var $layer = document.createElement('div');
        $layer.setAttribute('class', 'close-layer');

        this.sidebarVisible = false;
        body.classList.remove('nav-open');
        // $('html').removeClass('nav-open');
        body.classList.remove('nav-open');
        if ($layer) {
            $layer.remove();
        }

        setTimeout(function () {
            $toggle.classList.remove('toggled');
        }, 400);

        this.mobile_menu_visible = 0;
    };
    sidebarToggle() {
        if (this.sidebarVisible === false) {
            this.sidebarOpen();
        } else {
            this.sidebarClose();
        }
    }

    getTitle() {
        this.found = false;
        let titlee: any = this.location.prepareExternalUrl(this.location.path()).replace('#', '');
        for (let i = 0; i < this.listTitles.length; i++) {
            if (this.listTitles[i].type === "link" && this.listTitles[i].path === titlee) {
                return this.listTitles[i].title;
            } else if (this.listTitles[i].type === "sub") {
                this.armar = this.listTitles[i].path;
                if (!this.found) {
                    this.breadCrum(this.listTitles[i], true);
                } else {
                    return this.titulo;
                }

                for (let j = 0; j < this.listTitles[i].children.length; j++) {
                    let subtitle = this.listTitles[i].path + '/' + this.listTitles[i].children[j].path;
                    if (subtitle === titlee) {
                        return this.listTitles[i].children[j].title;
                    }
                }




               for (let j = 0; j < this.listTitles[i].children.length; j++) {
                    if(this.listTitles[i].children[j].children ){
                        for(let k = 0; k < this.listTitles[i].children[j].children.length; k++){
                            let subtitle = this.listTitles[i].path + '/' + this.listTitles[i].children[j].path + '/' + this.listTitles[i].children[j].children[k].path;
                            if (subtitle === titlee){
                                return this.listTitles[i].children[j].children[k].title;
                            }
                        }
                    }
                }
            } else if (this.listTitles[i].type === "multi-sub") { // CAMBIAR ESTE METODO A UNO RECURSIVO YA QUE DE MOMENTO SOLO ACEPTA DOS NIVELES
                for (let j = 0; j < this.listTitles[i].children.length; j++) {
                    let subtitle = this.listTitles[i].path + '/' + this.listTitles[i].children[j].path;
                    if (subtitle === titlee) {
                        return this.listTitles[i].children[j].title;
                    }
                }

                for (let j = 0; j < this.listTitles[i].children.length; j++) {
                    for (let k = 0; k < this.listTitles[i].children[j].children.length; k++) {
                        let subtitle = this.listTitles[i].path + '/' + this.listTitles[i].children[j].path + '/' + this.listTitles[i].children[j].children[k].path;
                        if (subtitle === titlee) {
                            return this.listTitles[i].children[j].children[k].title;
                        }
                    }
                }
            } else if (this.listTitles[i].type === "header" && this.listTitles[i].path === titlee) {

                return this.listTitles[i].title;

            } else if (this.listTitles[i].type === "header") {
                if (this.listTitles[i].children != undefined) {
                    for (let j = 0; j < this.listTitles[i].children.length; j++) {
                        let subtitle = this.listTitles[i].path + '/' + this.listTitles[i].children[j].path;
                        if (subtitle === titlee) {
                            return this.listTitles[i].title + " " + this.listTitles[i].children[j].title;
                        }
                    }
                }
            }
        }
        //console.log(titlee);
        return 'Dashboard';
    }

    getPath() {
        return this.location.prepareExternalUrl(this.location.path());
    }
 

    breadCrum(node, x) {
        let ultima = '';

        let titlee: any = this.location.prepareExternalUrl(this.location.path()).replace('#', '');

        if (!x) {
            this.armar = this.armar + '/' + node.path;
            ultima = node.path;
        }
        if (titlee === this.armar) {
            this.found = true;
            this.titulo = node.title;
            return this.titulo;
        } else {
            if (node.children) {
                if (node.children.length >= 0) {
                    for (var i = 0; i < node.children.length; i++) {
                        this.breadCrum(node.children[i], false);
                        if (this.found) {
                            break;
                        }
                    }
                    let ruta = this.armar.split('\/');
                    ruta = ruta.slice(0, ruta.length - 1);
                    this.armar = ruta.join('/');
                }
            } else {
                if (titlee === this.armar) {
                    return node.title;
                } else {
                    let ruta = this.armar.split('\/');
                    ruta = ruta.slice(0, ruta.length - 1);
                    this.armar = ruta.join('/');
                }
            }
        }

    }

}
