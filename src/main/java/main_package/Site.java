package main_package;

public enum Site {
    S,  // susceptible
    I,  // infectious
    R,  // removed
    V;  // vaccinated


    public Site next(Model model)
    {
        switch(model)
        {
            case SIR -> {
                switch(this)
                {
                    case S -> {
                        return I;
                    }
                    case I -> {
                        return R;
                    }
                    case R -> {
                        return S;
                    }
                    case V -> throw new IllegalArgumentException(this.name() + " in SIR model");
                }

            }
            case SIS -> {
                switch(this)
                {
                    case S -> {
                        return I;
                    }
                    case I -> {
                        return S;
                    }
                    case R, V -> throw new IllegalArgumentException(this.name() + " in SIR model");
                }

            }
            case SIRV -> {
                switch(this)
                {
                    case S -> {
                        return I;
                    }
                    case I -> {
                        return R;
                    }
                    case R -> {
                        return V;
                    }
                    case V -> {
                        return S;
                    }
                }
            }
        }

        return this;
    }
}
