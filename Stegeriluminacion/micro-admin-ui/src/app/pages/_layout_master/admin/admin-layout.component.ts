import { Location, PopStateEvent } from '@angular/common';
import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { NavigationCancel, NavigationEnd, NavigationError, NavigationStart, Router } from '@angular/router';
import PerfectScrollbar from 'perfect-scrollbar';
import 'rxjs/add/operator/filter';
import { Subscription } from 'rxjs/Subscription';
import { UtilComponent } from 'src/app/_shared/util.component';
import { NavItem } from '../../_template/md/md.module';
import { NavbarComponent } from '../navbar/navbar.component';

declare const $: any;

@Component({
  selector: 'app-layout',
  templateUrl: './admin-layout.component.html'
})

export class AdminLayoutComponent implements OnInit, AfterViewInit {
  public navItems: NavItem[];
  private _router: Subscription;
  private lastPoppedUrl: string;
  private yScrollStack: number[] = [];
  url: string;
  location: Location;

  @ViewChild('sidebar') sidebar: any;
  @ViewChild(NavbarComponent) navbar: NavbarComponent;

  constructor(private router: Router
    , location: Location
    , public utilComponent: UtilComponent) {
    this.location = location;
  }
  ngOnInit() {

    const elemMainPanel = <HTMLElement>document.querySelector('.main-panel');
    const elemSidebar = <HTMLElement>document.querySelector('.sidebar .sidebar-wrapper');
    this.location.subscribe((ev: PopStateEvent) => {
      this.lastPoppedUrl = ev.url;
    });

    this.router.events.subscribe((event: any) => {
      if (event instanceof NavigationStart) { // INICIO DE LA NAVEGACION

        if (event.url != this.lastPoppedUrl)
          this.yScrollStack.push(window.scrollY);

      } else if (event instanceof NavigationEnd) { // FIN DE LA NAVEGACION

        if (event.url == this.lastPoppedUrl) {
          this.lastPoppedUrl = undefined;
          window.scrollTo(0, this.yScrollStack.pop());
        }
        else
          window.scrollTo(0, 0);
      } else if (event instanceof NavigationCancel) { // NAVEGACION CANCELADA

      } else if (event instanceof NavigationError) {// NAVEGACION ERRADA

      } else {
      }

    });


    this._router = this.router.events.filter(event => event instanceof NavigationEnd).subscribe((event: NavigationEnd) => {
      elemMainPanel.scrollTop = 0;
      elemSidebar.scrollTop = 0;
    });
    const html = document.getElementsByTagName('html')[0];
    if (window.matchMedia(`(min-width: 960px)`).matches && !this.isMac()) {
      let ps = new PerfectScrollbar(elemMainPanel);
      ps = new PerfectScrollbar(elemSidebar);
      html.classList.add('perfect-scrollbar-on');
    }
    else {
      html.classList.add('perfect-scrollbar-off');
    }

    this._router = this.router.events.filter(event => event instanceof NavigationEnd).subscribe((event: NavigationEnd) => {
      this.navbar.sidebarClose();
    });

  }

  ngAfterViewInit() {
    this.runOnRouteChange();
  }
  public isMap() {
    if (this.location.prepareExternalUrl(this.location.path()) === '/maps/fullscreen') {
      return true;
    } else {
      return false;
    }
  }
  runOnRouteChange(): void {
    if (window.matchMedia(`(min-width: 960px)`).matches && !this.isMac()) {
      const elemSidebar = <HTMLElement>document.querySelector('.sidebar .sidebar-wrapper');
      const elemMainPanel = <HTMLElement>document.querySelector('.main-panel');
      let ps = new PerfectScrollbar(elemMainPanel);
      ps = new PerfectScrollbar(elemSidebar);
      ps.update();
    }
  }
  isMac(): boolean {
    let bool = false;
    if (navigator.platform.toUpperCase().indexOf('MAC') >= 0 || navigator.platform.toUpperCase().indexOf('IPAD') >= 0) {
      bool = true;
    }
    return bool;
  }
}
