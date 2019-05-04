import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IAdventureRace, AdventureRace } from 'app/shared/model/adventure-race.model';
import { AdventureRaceService } from './adventure-race.service';
import { IAdventureRaceOptions } from 'app/shared/model/adventure-race-options.model';
import { AdventureRaceOptionsService } from 'app/entities/adventure-race-options';
import { IAdventureFraction } from 'app/shared/model/adventure-fraction.model';
import { AdventureFractionService } from 'app/entities/adventure-fraction';

@Component({
  selector: 'jhi-adventure-race-update',
  templateUrl: './adventure-race-update.component.html'
})
export class AdventureRaceUpdateComponent implements OnInit {
  adventureRace: IAdventureRace;
  isSaving: boolean;

  adventureraceoptions: IAdventureRaceOptions[];

  adventurefractions: IAdventureFraction[];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    desc: [null, [Validators.required]],
    image: [null, [Validators.required]],
    imageContentType: [],
    adventureRaceOptionsId: [],
    adventureFractions: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected adventureRaceService: AdventureRaceService,
    protected adventureRaceOptionsService: AdventureRaceOptionsService,
    protected adventureFractionService: AdventureFractionService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ adventureRace }) => {
      this.updateForm(adventureRace);
      this.adventureRace = adventureRace;
    });
    this.adventureRaceOptionsService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IAdventureRaceOptions[]>) => mayBeOk.ok),
        map((response: HttpResponse<IAdventureRaceOptions[]>) => response.body)
      )
      .subscribe(
        (res: IAdventureRaceOptions[]) => (this.adventureraceoptions = res),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.adventureFractionService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IAdventureFraction[]>) => mayBeOk.ok),
        map((response: HttpResponse<IAdventureFraction[]>) => response.body)
      )
      .subscribe((res: IAdventureFraction[]) => (this.adventurefractions = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(adventureRace: IAdventureRace) {
    this.editForm.patchValue({
      id: adventureRace.id,
      name: adventureRace.name,
      desc: adventureRace.desc,
      image: adventureRace.image,
      imageContentType: adventureRace.imageContentType,
      adventureRaceOptionsId: adventureRace.adventureRaceOptionsId,
      adventureFractions: adventureRace.adventureFractions
    });
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }

  setFileData(event, field: string, isImage) {
    return new Promise((resolve, reject) => {
      if (event && event.target && event.target.files && event.target.files[0]) {
        const file = event.target.files[0];
        if (isImage && !/^image\//.test(file.type)) {
          reject(`File was expected to be an image but was found to be ${file.type}`);
        } else {
          const filedContentType: string = field + 'ContentType';
          this.dataUtils.toBase64(file, base64Data => {
            this.editForm.patchValue({
              [field]: base64Data,
              [filedContentType]: file.type
            });
          });
        }
      } else {
        reject(`Base64 data was not set as file could not be extracted from passed parameter: ${event}`);
      }
    }).then(
      () => console.log('blob added'), // sucess
      this.onError
    );
  }

  clearInputImage(field: string, fieldContentType: string, idInput: string) {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null
    });
    if (this.elementRef && idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const adventureRace = this.createFromForm();
    if (adventureRace.id !== undefined) {
      this.subscribeToSaveResponse(this.adventureRaceService.update(adventureRace));
    } else {
      this.subscribeToSaveResponse(this.adventureRaceService.create(adventureRace));
    }
  }

  private createFromForm(): IAdventureRace {
    const entity = {
      ...new AdventureRace(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      desc: this.editForm.get(['desc']).value,
      imageContentType: this.editForm.get(['imageContentType']).value,
      image: this.editForm.get(['image']).value,
      adventureRaceOptionsId: this.editForm.get(['adventureRaceOptionsId']).value,
      adventureFractions: this.editForm.get(['adventureFractions']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAdventureRace>>) {
    result.subscribe((res: HttpResponse<IAdventureRace>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackAdventureRaceOptionsById(index: number, item: IAdventureRaceOptions) {
    return item.id;
  }

  trackAdventureFractionById(index: number, item: IAdventureFraction) {
    return item.id;
  }

  getSelected(selectedVals: Array<any>, option: any) {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
