import { HttpErrorResponse } from '@angular/common/http';
import { Component, ElementRef, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { UtilComponent } from 'src/app/_shared/util.component';
import { configuraciones } from 'src/environments/configuraciones';
import swal from 'sweetalert2';
import { LoginService } from 'src/app/_servicios/login.service';
import { Token } from 'src/app/_dto/Token.Dto';
import { LoginDTO } from 'src/app/_dto/Login.Dto';

declare var $: any;

@Component({
    selector: 'app-login-cmp',
    templateUrl: './login.component.html'
})

export class LoginComponent implements OnInit, OnDestroy {
    test: Date = new Date();
    private toggleButton: any;
    private sidebarVisible: boolean;
    private nativeElement: Node;

    public loginDTO: LoginDTO;
    public loginFG: FormGroup;

    constructor(
        private translate: TranslateService
        , private element: ElementRef
        , private formBuilder: FormBuilder
        , private loginService: LoginService
        , public router: Router
        , public utilComponent: UtilComponent) {
        this.nativeElement = element.nativeElement;
        this.sidebarVisible = false;

        this.loginFG = this.formBuilder.group({
            usuario: ['', Validators.required],
            password: ['', Validators.compose([Validators.required, Validators.minLength(6)])],
        });
    }

    ngOnInit() {
        var navbar: HTMLElement = this.element.nativeElement;
        this.toggleButton = navbar.getElementsByClassName('navbar-toggle')[0];
        const body = document.getElementsByTagName('body')[0];
        body.classList.add('login-page');
        body.classList.add('off-canvas-sidebar');
        const card = document.getElementsByClassName('card')[0];
        setTimeout(function () {
            // after 1000 ms we add the class animated to the login/register card
            card.classList.remove('card-hidden');
        }, 700);
        this.loginDTO = new LoginDTO();
    }



    iniciarSesion() {
        this.utilComponent.loading = true;
        this.loginService.iniciarSesion(this.loginDTO).subscribe(
            respuesta => {
                let token = respuesta;
                localStorage.setItem(configuraciones.static.token, JSON.stringify(token));
                console.log("dashboard");
                this.router.navigate(['dashboard']);
                this.utilComponent.loading = false;
            },
            (error: HttpErrorResponse) => {
                console.log("ERROR: " + JSON.stringify(error));
                this.utilComponent.loading = false;
            });
    }

    sidebarToggle() {
        var toggleButton = this.toggleButton;
        var body = document.getElementsByTagName('body')[0];
        var sidebar = document.getElementsByClassName('navbar-collapse')[0];
        if (this.sidebarVisible == false) {
            setTimeout(function () {
                toggleButton.classList.add('toggled');
            }, 500);
            body.classList.add('nav-open');
            this.sidebarVisible = true;
        } else {
            this.toggleButton.classList.remove('toggled');
            this.sidebarVisible = false;
            body.classList.remove('nav-open');
        }
    }

    ngOnDestroy() {
        const body = document.getElementsByTagName('body')[0];
        body.classList.remove('login-page');
        body.classList.remove('off-canvas-sidebar');
    }

}
