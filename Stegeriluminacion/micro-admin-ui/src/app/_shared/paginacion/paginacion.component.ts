import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';

@Component({
    selector: 'app-pagination',
    templateUrl: 'paginacion.component.html',
})
export class PaginacionComponent implements OnInit {

    anteriorS = '<';
    siguienteS = '>';
    paginas: number[];
    totalPaginas: number;

    inicio = 0;
    fin = 5;

    @Input() public page: number;
    @Input()
    set totalPages(totalPages: number) {
        this.totalPaginas = totalPages;
        this.paginas = [];
        let p = 0;
        console.log(this.totalPages);
        for (var i = 0; i < this.totalPages; i++) {
            p = p + 1;
            this.paginas.push(p);
        }
        console.log(this.paginas);
    }
    get totalPages() {
        return this.totalPaginas;
    }
    @Input() public numShops: number;
    @Output() paginaEmitter: EventEmitter<number> = new EventEmitter();



    constructor() {
    }
    ngOnInit() {

    }

    siguiente() {
        this.page++;
        this.pasarPagina();
    }
    anterior() {
        this.page--;
        this.pasarPagina();
    }

    irPagina(pagina) {
        this.page = pagina;
        this.pasarPagina();
    }

    activado(pagina) {
        let stilo = '';
        if (pagina === this.page) {
            stilo = 'activado';
        }
        return stilo;
    }

    centro(pagina) {
        let stilo = 'centroPaginado';
        if (pagina === this.page) {
            stilo = 'centroPaginadoC';
            if (pagina >= 10) {
                stilo = 'centroPaginado10 row';
            }
        }
        return stilo;
    }


    pasarPagina() {
        this.fin = this.page + 2;
        if (this.page < 3) {
            this.inicio = 0;
            this.fin = 5;
        } else {
            this.inicio = this.page - 3;
        }
        if (this.page === 1) {
            this.fin = 5;
        }
        this.paginaEmitter.emit(this.page);
    }
}