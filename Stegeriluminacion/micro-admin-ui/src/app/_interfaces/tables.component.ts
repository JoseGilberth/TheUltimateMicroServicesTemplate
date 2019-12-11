import { HostListener } from '@angular/core';

export class TableComponent {

  public showScreenAlert: boolean = false;
 
  @HostListener('window:resize', ['$event'])
  onResize(event) { 
    if (window.innerWidth < 1200) { // 768px portrait
      this.showScreenAlert = true;
    } else {
      this.showScreenAlert = false;
    }
  }


}
