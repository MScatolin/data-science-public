## Marcelo Scatolin Queiroz
# Project 1 - W200 - Python for Data Science
# UC Berkeley - Master of Information and Data Science.
# Spring 2018 cohort

# This project consists of three classes and one main program. The idea is to
# raise awareness of how much food we can produce in a given land, and the
# nuances of each culture when dealing with variables like rain, declivity,
# soil fertility and cost.
# For more detail, please refer to the documentation on each class.

import sys
from collections import defaultdict


class Land():
    """This class initializes a map, of 8 x 8 whose rows are numbered from
    1 to 8 top to bottom and columns are referenced as letter from 'a' to 'h',
    like in a chess board.

    The characteristics from the map are set and can be shown in few maps that
    can be printed in the prompt like in the following example:

                                  ANNUAL RAIN

	         A       B       C       D	     E       F       G       H
         +-------+-------+-------+-------+-------+-------+-------+-------+
1 	     |   1   |   2   |   1   |   2   |   1   |   1   |   1   |   2   |
         +-------+-------+-------+-------+-------+-------+-------+-------+
2 	     |   1   |   3   |   1   |   2   |   4   |   5   |   1   |   2   |
         +-------+-------+-------+-------+-------+-------+-------+-------+
3 	     |   0   |   1   |   5   |   1   |   9   |   8   |   1   |   0   |
         +-------+-------+-------+-------+-------+-------+-------+-------+
4 	     |   0   |   1   |   5   |   1   |   1   |   9   |   7   |   1   |
         +-------+-------+-------+-------+-------+-------+-------+-------+
5 	     |   1   |   1   |   1   |   2   |   1   |   1   |   1   |   2   |
         +-------+-------+-------+-------+-------+-------+-------+-------+
6 	     |   1   |   5   |   1   |   2   |   1   |   1   |   1   |   2   |
         +-------+-------+-------+-------+-------+-------+-------+-------+
7 	     |   1   |   3   |   4   |   2   |   1   |   1   |   1   |   2   |
         +-------+-------+-------+-------+-------+-------+-------+-------+
8 	     |   1   |   2   |   1   |   1   |   1   |   1   |   1   |   2   |
         +-------+-------+-------+-------+-------+-------+-------+-------+

    Funtions available for this class:
    - default_map()
    - get_properties()

    Refer to each function documentation for details.

    WARNING: This class overides the __str__ and __repr__ functions to provide
             better vizualition.

    """
    def __init__(self):
        self.current_map = self.default_map()


    def default_map(self):
        """
        Creates a default map in 8 x 8 format.

        The map has the structure of a nested dictionary:

         {'property':   {'row_line':    'map_cell':     'property_of_cell'}

         where property_of_cell is a normalized number between 0 and 10.
         """

        current_map = {'Average Rainfall':{1:{('A',1):1, ('B',1):1, ('C',1):0, ('D',1):1,
                                              ('E',1):0, ('F',1):1, ('G',1):1, ('H',1):1},
                                           2:{('A',2):1, ('B',2):1, ('C',2):1, ('D',2):1,
                                              ('E',2):1, ('F',2):1, ('G',2):1, ('H',2):0},
                                           3:{('A',3):0, ('B',3):1, ('C',3):1, ('D',3):0,
                                              ('E',3):4, ('F',3):1, ('G',3):1, ('H',3):0},
                                           4:{('A',4):2, ('B',4):1, ('C',4):2, ('D',4):2,
                                              ('E',4):7, ('F',4):4, ('G',4):4, ('H',4):1},
                                           5:{('A',5):2, ('B',5):1, ('C',5):2, ('D',5):6,
                                              ('E',5):8, ('F',5):6, ('G',5):3, ('H',5):2},
                                           6:{('A',6):2, ('B',6):2, ('C',6):3, ('D',6):3,
                                              ('E',6):6, ('F',6):5, ('G',6):3, ('H',6):1},
                                           7:{('A',7):2, ('B',7):1, ('C',7):0, ('D',7):2,
                                              ('E',7):2, ('F',7):2, ('G',7):2, ('H',7):1},
                                           8:{('A',8):0, ('B',8):0, ('C',8):0, ('D',8):0,
                                              ('E',8):1, ('F',8):1, ('G',8):1, ('H',8):1}},
                         'Soil Fertility':{1:{('A',1):1, ('B',1):0, ('C',1):2, ('D',1):3,
                                              ('E',1):4, ('F',1):5, ('G',1):4, ('H',1):4},
                                           2:{('A',2):1, ('B',2):1, ('C',2):2, ('D',2):4,
                                              ('E',2):4, ('F',2):6, ('G',2):5, ('H',2):5},
                                           3:{('A',3):0, ('B',3):3, ('C',3):5, ('D',3):7,
                                              ('E',3):6, ('F',3):8, ('G',3):7, ('H',3):5},
                                           4:{('A',4):2, ('B',4):3, ('C',4):3, ('D',4):4,
                                              ('E',4):7, ('F',4):7, ('G',4):8, ('H',4):3},
                                           5:{('A',5):1, ('B',5):3, ('C',5):4, ('D',5):6,
                                              ('E',5):5, ('F',5):6, ('G',5):5, ('H',5):4},
                                           6:{('A',6):1, ('B',6):0, ('C',6):0, ('D',6):1,
                                              ('E',6):3, ('F',6):4, ('G',6):2, ('H',6):2},
                                           7:{('A',7):2, ('B',7):1, ('C',7):0, ('D',7):1,
                                              ('E',7):3, ('F',7):4, ('G',7):4, ('H',7):1},
                                           8:{('A',8):1, ('B',8):2, ('C',8):0, ('D',8):0,
                                              ('E',8):1, ('F',8):1, ('G',8):2, ('H',8):1}},
                              'Declivity':{1:{('A',1):0, ('B',1):1, ('C',1):0, ('D',1):1,
                                              ('E',1):0, ('F',1):1, ('G',1):1, ('H',1):1},
                                           2:{('A',2):1, ('B',2):2, ('C',2):2, ('D',2):2,
                                              ('E',2):3, ('F',2):4, ('G',2):3, ('H',2):1},
                                           3:{('A',3):1, ('B',3):2, ('C',3):3, ('D',3):2,
                                              ('E',3):4, ('F',3):3, ('G',3):3, ('H',3):2},
                                           4:{('A',4):3, ('B',4):4, ('C',4):6, ('D',4):6,
                                              ('E',4):5, ('F',4):4, ('G',4):5, ('H',4):3},
                                           5:{('A',5):4, ('B',5):5, ('C',5):5, ('D',5):4,
                                              ('E',5):6, ('F',5):4, ('G',5):4, ('H',5):2},
                                           6:{('A',6):4, ('B',6):6, ('C',6):7, ('D',6):8,
                                              ('E',6):5, ('F',6):4, ('G',6):4, ('H',6):3},
                                           7:{('A',7):4, ('B',7):7, ('C',7):9, ('D',7):8,
                                              ('E',7):6, ('F',7):7, ('G',7):4, ('H',7):4},
                                           8:{('A',8):3, ('B',8):4, ('C',8):4, ('D',8):4,
                                              ('E',8):5, ('F',8):4, ('G',8):3, ('H',8):2}},
                         'Available Area':{1:{('A',1):7, ('B',1):6, ('C',1):7, ('D',1):5,
                                              ('E',1):5, ('F',1):4, ('G',1):2, ('H',1):1},
                                           2:{('A',2):6, ('B',2):8, ('C',2):7, ('D',2):6,
                                              ('E',2):5, ('F',2):3, ('G',2):4, ('H',2):2},
                                           3:{('A',3):9, ('B',3):9, ('C',3):8, ('D',3):6,
                                              ('E',3):5, ('F',3):5, ('G',3):4, ('H',3):3},
                                           4:{('A',4):7, ('B',4):7, ('C',4):6, ('D',4):5,
                                              ('E',4):3, ('F',4):5, ('G',4):2, ('H',4):1},
                                           5:{('A',5):4, ('B',5):4, ('C',5):6, ('D',5):5,
                                              ('E',5):4, ('F',5):3, ('G',5):3, ('H',5):4},
                                           6:{('A',6):2, ('B',6):2, ('C',6):3, ('D',6):5,
                                              ('E',6):6, ('F',6):7, ('G',6):7, ('H',6):8},
                                           7:{('A',7):1, ('B',7):2, ('C',7):1, ('D',7):3,
                                              ('E',7):4, ('F',7):8, ('G',7):9, ('H',7):5},
                                           8:{('A',8):4, ('B',8):5, ('C',8):4, ('D',8):3,
                                              ('E',8):2, ('F',8):7, ('G',8):8, ('H',8):2}}}

        return current_map

    def get_properties(self, cell):
        """
        Returns a tuple with the properties in a given cell of the loaded map.
        Example of return:

        >>>land.get_properties(('a',1))
        (5, 4, 3, 2)
        """

        column = cell[0]
        row = cell[1]
        water = self.current_map['Average Rainfall'][row][cell]
        soil = self.current_map['Soil Fertility'][row][cell]
        dec = self.current_map['Declivity'][row][cell]
        area = self.current_map['Available Area'][row][cell]
        return (water, soil, dec, area)

    def __str__(self):
        """Prints the maps as stated in the documentation of classes"""

        print ('Your current map is: ')
        for map_type in self.current_map:
            print ('\n',map_type.upper(),'\n')
            #column_count = 1
            #row_count = 1
            print('      ', end='')
            for column in 'ABCDEFGH':
                print('    '+column+'   ', end = '')
            print ('\n'+'     ', 8 * '+-------'+'+')

            for row_count in self.current_map[map_type]:
                print(str(row_count)+'     ',end = '')
                for cell in self.current_map[map_type][row_count]:
                    print('|   '+str(self.current_map[map_type][row_count][cell])
                          +'   ', end = '')
                print('|')
                print ('     ', 8 * '+-------'+'+')

        return ''

    def __repr__(self):
        return self.__str__()


class Culture():
    """
    This class implements cultures that can be planted in the class land.
    each culture have a mathematical function that tells what is that culture
    yield according to 4 parameters:
        - Average Rainfall
        - Soil Fertility
        - Declivity
        - Total Area

    There are 4 cultures implemented as default, but more can be added.

    Funtions available for this class:
    - get_name()
    - production()

    Refer to each function documentation for details.

    WARNING: This class overides the __str__ and __repr__ functions to provide
             better vizualition.
    """

    def __init__(self, name, base_prod, rain_ndd, fert_ndd, dec_actd):
        self.name = name
        self.base_prod = base_prod
        self.rain_ndd = rain_ndd
        self.fert_ndd = fert_ndd
        self.dec_actd = dec_actd

    def get_name(self):
        """Returns the name of the culture for printing purposes"""
        return self.name

    def production(self, current_map, cell):
        """ Returns the predicted production based on the properties of the used
        cell and in the properties expected by the calling culture.

        Example:
        >>> culture.production(land,('B',1))
        ['Soy beans', 15.67]
        """

        cell_prop = current_map.get_properties(cell)

        regular_prod = self.base_prod * cell_prop[3]

        delta_water = 1 - abs(cell_prop[0] - self.rain_ndd)/10

        #functions to calculate delta production for each property:

        if self.fert_ndd < cell_prop[1]:
            delta_fert = 1 - (cell_prop[1] - self.fert_ndd) * 0.5 / 10
        else:
            delta_fert = 1 + (self.fert_ndd - cell_prop[1]) / 10

        if self.dec_actd < cell_prop[2]:
            delta_dec = 1 + (cell_prop[2] - self.dec_actd) * (2) / 10
        else:
            delta_dec = 0

        delta_prod = 1 - (delta_fert * 0.5 - delta_water * 0.3 - delta_dec * 0.2)

        total_prod = delta_prod * regular_prod

        print('This will produce {:.2f} tons of {}'.format(total_prod, self.name))

        return [self.name, total_prod]

    def __str__(self):
        print('\nCulture:',self.name+'.')
        print('Base Production: {:.2f} tons/hectare'.format(self.base_prod))
        print('Water needed:', self.rain_ndd, 'in a scale 0 ~ 9')
        print('Soil fertility needed:', self.fert_ndd, 'in a scale 0 ~ 9')
        print('Declivity accepted to harvest:', self.dec_actd, 'in a scale 0 ~ 9\n')


        return ''

    def __repr__(self):
        self.__str__()


class Warehouse():
    '''Class that wil store the type of food, the tonnage stored of each and
    the total tonnage.

    functions available for this class;
    - store_production()
    - get_total()

    Refer to each function documentation for details.

    WARNING: This class overides the __str__ and __repr__ functions to provide
             better vizualition.'''

    def __init__(self):
        self.items_produced = defaultdict(int)
        self.areas_cultivated = 0

    def store_production(self, production):
        """
        update a class attribute of type defaultdict with a new entry.
        """
        self.items_produced[production[0]] += production[1]
        print('Stored {:.2f} tons of {}'.format(production[1], production[0]))
        self.areas_cultivated += 1

    def get_total(self):
        """
        Iterate through class attribute of type defaultdict adding the
        production value and returns an int with the added production of all
        cultures in tons.
        """
        total_production = 0
        for value  in self.items_produced.values():
            total_production += value

        return total_production

    def __str__(self):
        """
        Prints the number of cultivated areas, each type of culture separated
        and the added production.
        """

        if self.areas_cultivated > 0:
            print('\nThere are currently', self.areas_cultivated, 'area(s) cultivated.')
            print('And you produced:\n')
            for item, value in self.items_produced.items():
                print('- {:.2f} tons of {}'.format(value,item))
            print ('\n Or a total of', self.get_total(), 'tons of food. Congratulations')
        else:
            print("You haven't produced nothing yet")

        return ''

    def __repr__(self):
        return self.__str__()


 ##################################################################################
 #  MAIN PROGRAM

def validate_culture(base_prod, rain_ndd, fert_ndd, dec_actd):
    """
    Returns true if the types of a custom culture are well inputed. Case this is
    not true, returns false.
    """
    try:
        float(base_prod)
    except Exception:
        return False

    if rain_ndd not in ['0','1','2','3','4','5','6','7','8','9','10']:
        return False

    elif fert_ndd not in ['0','1','2','3','4','5','6','7','8','9','10']:
        return False

    elif dec_actd not in ['0','1','2','3','4','5','6','7','8','9','10']:
        return False

    else:
        return True

def parse_cell(cell):
    """
    check if the given cell is in correc format, i.e.:
    - letter from a to h
    - comma
    - integer from 1 to 8

    format: a,1 or A,1

    Case not true return a print statement: Not a valid option. Try again.
    """
    splited = cell.split(',')
    try:
        if splited[0].lower() in ['a','b','c','d','e','f','g','h'] and \
        int(splited[1]) in [1,2,3,4,5,6,7,8]:
            return (splited[0].upper(), int(splited[1]))

        else:
            print('Not a valid option. Try again.')

    except Exception:
        print('Not a valid option. Try again.')

if __name__ == "__main__":

    print('''Welcome to the farm simulator!

        Here you can approximate how water and soil charactheristics can influence food production
        You will use a map that looks like a chess board with four properties for each cell:
            - Average rainfall (from 0 to 10, with 10 being the most wet land, bad to sensitive cultures);
            - Soil fertility (from 0 to 10, with 10 being extremelly fertile lands, perfect for planting everything);
            - Land declivity (from 0 to 10, with 10 being really steep lands, almost impossible to harvest;
            - Area available for planting (from 0 to 10, with 10 being extensive areas.)

        For each cell you can assign one culture and find out what would be the production of a few cultures.
        After all, you can store you production into your warehouse and see how much food you grow!
        Choose an option using the keys in [].

        ''')

    soy = Culture('Soy beans', 3.5, 5, 7, 2)
    corn = Culture('Corn', 8, 3, 4, 5)
    rice = Culture('Rice', 6, 8, 2, 1)
    wheat = Culture('Wheat', 5, 2, 5, 4)

    cultures = [soy, corn, rice, wheat]

    storage = Warehouse()

    land = Land()

    run = True

    while run:

        decision = input ('''
        Type an option:

        - See [M]aps
        - See [C]ultures
        - See [W]arehouse
        - Create new [P]lantation
        - Create new culture [T]ype
        - [Q]uitType you option now:\n''')

        if decision.lower() == 'm':
            print(land)

        if decision.lower() == 'c':
            for culture in cultures:
                print(culture)

        if decision.lower() == 'w':
            print(storage)

        if decision.lower() == 'p':
            if input('Want to see maps again? [Y]es or any key to NO.').lower() == 'y':
                print(land)


            print('These are the available cultures: \n')
            for i in range (len(cultures)):
                print("["+str(i)+"] for", cultures[i].get_name())


            cult_for_plant = cultures[int(input('\nYour option:'))]
            cell_for_plant = parse_cell(input('Area for plant (format e.g.: A,1): '))

            predicted_production = cult_for_plant.production(land,cell_for_plant)

            store = input('Do you want to store this production? [Y]es or any key for No.')

            if store.lower() == 'y':
                storage.store_production(predicted_production)

                #except Exception:
                 #   print('Not a valid option. Try again.')

        if decision.lower() == 't':
            name = input("Input your culture's name: ")
            base_prod = input("Input your base production: ")
            rain_ndd = input("Input the water needed (0 ~ 10): ")
            fert_ndd = input("Input the soil fertility needed (0 ~ 10): ")
            dec_actd = input("input the declivity accepted for the landscape (0 ~ 10): ")

            if validate_culture(base_prod, rain_ndd, fert_ndd, dec_actd):
                new_culture = Culture(name, float(base_prod), int(rain_ndd), int(fert_ndd), int(dec_actd))
                cultures.append(new_culture)

            else:
                print('\nLooks like there is something wrong with your parameters, try again:')


        elif decision.lower() == "quit" or decision.lower() == "q":
            run = False
