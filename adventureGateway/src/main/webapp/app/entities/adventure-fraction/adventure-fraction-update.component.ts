import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IAdventureFraction, AdventureFraction } from 'app/shared/model/adventure-fraction.model';
import { AdventureFractionService } from './adventure-fraction.service';
import { IAdventureRace } from 'app/shared/model/adventure-race.model';
import { AdventureRaceService } from 'app/entities/adventure-race';

@Component({
  selector: 'jhi-adventure-fraction-update',
  templateUrl: './adventure-fraction-update.component.html'
})
export class AdventureFractionUpdateComponent implements OnInit {
  adventureFraction: IAdventureFraction;
  isSaving: boolean;

  adventureraces: IAdventureRace[];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    desc: [null, [Validators.required]],
    image: [null, [Validators.required]],
    imageContentType: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected adventureFractionService: AdventureFractionService,
    protected adventureRaceService: AdventureRaceService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ adventureFraction }) => {
      this.updateForm(adventureFraction);
      this.adventureFraction = adventureFraction;
    });
    this.adventureRaceService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IAdventureRace[]>) => mayBeOk.ok),
        map((response: HttpResponse<IAdventureRace[]>) => response.body)
      )
      .subscribe((res: IAdventureRace[]) => (this.adventureraces = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(adventureFraction: IAdventureFraction) {
    this.editForm.patchValue({
      id: adventureFraction.id,
      name: adventureFraction.name,
      desc: adventureFraction.desc,
      image: adventureFraction.image,
      imageContentType: adventureFraction.imageContentType
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
    const adventureFraction = this.createFromForm();
    if (adventureFraction.id !== undefined) {
      this.subscribeToSaveResponse(this.adventureFractionService.update(adventureFraction));
    } else {
      this.subscribeToSaveResponse(this.adventureFractionService.create(adventureFraction));
    }
  }

  private createFromForm(): IAdventureFraction {
    const entity = {
      ...new AdventureFraction(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      desc: this.editForm.get(['desc']).value,
      imageContentType: this.editForm.get(['imageContentType']).value,
      image: this.editForm.get(['image']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAdventureFraction>>) {
    result.subscribe((res: HttpResponse<IAdventureFraction>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackAdventureRaceById(index: number, item: IAdventureRace) {
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
