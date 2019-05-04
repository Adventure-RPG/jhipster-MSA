import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAdventureAttributes } from 'app/shared/model/adventure-attributes.model';

@Component({
  selector: 'jhi-adventure-attributes-detail',
  templateUrl: './adventure-attributes-detail.component.html'
})
export class AdventureAttributesDetailComponent implements OnInit {
  adventureAttributes: IAdventureAttributes;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ adventureAttributes }) => {
      this.adventureAttributes = adventureAttributes;
    });
  }

  previousState() {
    window.history.back();
  }
}
