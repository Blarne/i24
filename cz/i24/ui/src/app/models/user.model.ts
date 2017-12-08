export class User {

  constructor(
    public id: number,
    public name: string = 'Miroslav Svoboda',
    public token: string,
    public role?: string,
	public email: string = 'ing.miroslav.svoboda@gmail.com'
  ) {}

  toString () {
    return JSON.stringify(this);
  }

}
