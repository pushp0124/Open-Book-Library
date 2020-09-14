import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-admin-card-action',
  templateUrl: './admin-card-action.component.html',
  styleUrls: ['./admin-card-action.component.css']
})
export class AdminCardActionComponent implements OnInit {

  @Input() styleExpression : string;
  @Input() cardHeading : string;
  @Input() cardContent : string;
  @Input() cardFooterText : string;
  @Input() cardIndex : number;
  @Output() cardAction = new EventEmitter<number>();
  constructor() { }

  ngOnInit() {
  }

  cardFooterClicked() {
    this.cardAction.emit(this.cardIndex);
  }

}
