import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IAdventureScript } from 'app/shared/model/adventure-script.model';

@Component({
  selector: 'jhi-adventure-script-detail',
  templateUrl: './adventure-script-detail.component.html'
})
export class AdventureScriptDetailComponent implements OnInit {
  adventureScript: IAdventureScript;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ adventureScript }) => {
      this.adventureScript = adventureScript;
    });
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }
  previousState() {
    window.history.back();
  }
}
