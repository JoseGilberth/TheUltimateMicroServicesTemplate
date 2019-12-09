// IMPORTANT: this is a plugin which requires jQuery for initialisation and data manipulation

import { OnInit } from '@angular/core';

declare const $: any;

export class NotificationComponent implements OnInit {

    ngOnInit() {
        var mainPanel = document.getElementsByClassName('main-panel')[0];
        $('.modal').on('shown.bs.modal', function () {
            mainPanel.classList.add('no-scroll');
        })
        $('.modal').on('hidden.bs.modal', function () {
            mainPanel.classList.remove('no-scroll');
        })
    }

    //const type = ['', 'info', 'success', 'warning', 'danger', 'rose', 'primary'];
    showNotification(mensaje: string, from: any, align: any, color: string) {

        $.notify({
            icon: 'notifications',
            message: mensaje
        }, {
            type: color,
            timer: 5000,
            placement: {
                from: from,
                align: align
            },
            z_index: 9999999,
            animate: {
                enter: 'animated shake',
                exit: 'animated fadeOutUp'
            },
            template:
                '<div data-notify="container" style="z-index: 9999999;" class="col-xs-11 col-sm-3 alert alert-{0} alert-with-icon" role="alert">' +
                '<button mat-raised-button type="button" aria-hidden="true" class="close" data-notify="dismiss">  <i class="material-icons">close</i></button>' +
                '<i class="material-icons" data-notify="icon">notifications</i> ' +
                '<span data-notify="title">{1}</span> ' +
                '<span data-notify="message">{2}</span>' +
                '<div class="progress" data-notify="progressbar">' +
                '<div class="progress-bar progress-bar-{0}" role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" style="width: 0%;"></div>' +
                '</div>' +
                '<a href="{3}" target="{4}" data-notify="url"></a>' +
                '</div>'
        });
    }
}
