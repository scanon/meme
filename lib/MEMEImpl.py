#BEGIN_HEADER
#END_HEADER

'''

Module Name:
MEME

Module Description:


'''
class MEME:

    #BEGIN_CLASS_HEADER
    #END_CLASS_HEADER

    def __init__(self, config): #config contains contents of config file in hash or 
                                #None if it couldn't be found
        #BEGIN_CONSTRUCTOR
        #END_CONSTRUCTOR
        pass

    def find_motifs_with_meme(self, sequenceSet, params):
        # self.ctx is set by the wsgi application class
        # return variables are: meme_run_result
        #BEGIN find_motifs_with_meme
        #END find_motifs_with_meme

        #At some point might do deeper type checking...
        if not isinstance(meme_run_result, dict):
            raise ValueError('Method find_motifs_with_meme return value meme_run_result is not type dict as required.')
        # return the results
        return [ meme_run_result ]
        
    def find_motifs_with_meme_from_ws(self, ws_id, sequence_set_id, params):
        # self.ctx is set by the wsgi application class
        # return variables are: meme_run_result_id
        #BEGIN find_motifs_with_meme_from_ws
        #END find_motifs_with_meme_from_ws

        #At some point might do deeper type checking...
        if not isinstance(meme_run_result_id, basestring):
            raise ValueError('Method find_motifs_with_meme_from_ws return value meme_run_result_id is not type basestring as required.')
        # return the results
        return [ meme_run_result_id ]
        
    def compare_motifs_with_tomtom(self, query, target, params):
        # self.ctx is set by the wsgi application class
        # return variables are: tomtom_run_result
        #BEGIN compare_motifs_with_tomtom
        #END compare_motifs_with_tomtom

        #At some point might do deeper type checking...
        if not isinstance(tomtom_run_result, dict):
            raise ValueError('Method compare_motifs_with_tomtom return value tomtom_run_result is not type dict as required.')
        # return the results
        return [ tomtom_run_result ]
        
    def compare_motifs_with_tomtom_from_ws(self, ws_id, query_id, target_id, params):
        # self.ctx is set by the wsgi application class
        # return variables are: tomtom_run_result_id
        #BEGIN compare_motifs_with_tomtom_from_ws
        #END compare_motifs_with_tomtom_from_ws

        #At some point might do deeper type checking...
        if not isinstance(tomtom_run_result_id, basestring):
            raise ValueError('Method compare_motifs_with_tomtom_from_ws return value tomtom_run_result_id is not type basestring as required.')
        # return the results
        return [ tomtom_run_result_id ]
        
    def compare_motifs_with_tomtom_by_collection(self, query, target, pspm_id, params):
        # self.ctx is set by the wsgi application class
        # return variables are: tomtom_run_result
        #BEGIN compare_motifs_with_tomtom_by_collection
        #END compare_motifs_with_tomtom_by_collection

        #At some point might do deeper type checking...
        if not isinstance(tomtom_run_result, dict):
            raise ValueError('Method compare_motifs_with_tomtom_by_collection return value tomtom_run_result is not type dict as required.')
        # return the results
        return [ tomtom_run_result ]
        
    def compare_motifs_with_tomtom_by_collection_from_ws(self, ws_id, query_id, target_id, pspm_id, params):
        # self.ctx is set by the wsgi application class
        # return variables are: tomtom_run_result_id
        #BEGIN compare_motifs_with_tomtom_by_collection_from_ws
        #END compare_motifs_with_tomtom_by_collection_from_ws

        #At some point might do deeper type checking...
        if not isinstance(tomtom_run_result_id, basestring):
            raise ValueError('Method compare_motifs_with_tomtom_by_collection_from_ws return value tomtom_run_result_id is not type basestring as required.')
        # return the results
        return [ tomtom_run_result_id ]
        
    def find_sites_with_mast(self, query, target, mt):
        # self.ctx is set by the wsgi application class
        # return variables are: mast_run_result
        #BEGIN find_sites_with_mast
        #END find_sites_with_mast

        #At some point might do deeper type checking...
        if not isinstance(mast_run_result, dict):
            raise ValueError('Method find_sites_with_mast return value mast_run_result is not type dict as required.')
        # return the results
        return [ mast_run_result ]
        
    def find_sites_with_mast_from_ws(self, ws_id, query_id, target_id, mt):
        # self.ctx is set by the wsgi application class
        # return variables are: mast_run_result_id
        #BEGIN find_sites_with_mast_from_ws
        #END find_sites_with_mast_from_ws

        #At some point might do deeper type checking...
        if not isinstance(mast_run_result_id, basestring):
            raise ValueError('Method find_sites_with_mast_from_ws return value mast_run_result_id is not type basestring as required.')
        # return the results
        return [ mast_run_result_id ]
        
    def find_sites_with_mast_by_collection(self, query, target, pspm_id, mt):
        # self.ctx is set by the wsgi application class
        # return variables are: mast_run_result
        #BEGIN find_sites_with_mast_by_collection
        #END find_sites_with_mast_by_collection

        #At some point might do deeper type checking...
        if not isinstance(mast_run_result, dict):
            raise ValueError('Method find_sites_with_mast_by_collection return value mast_run_result is not type dict as required.')
        # return the results
        return [ mast_run_result ]
        
    def find_sites_with_mast_by_collection_from_ws(self, ws_id, query_id, target_id, pspm_id, mt):
        # self.ctx is set by the wsgi application class
        # return variables are: mast_run_result_id
        #BEGIN find_sites_with_mast_by_collection_from_ws
        #END find_sites_with_mast_by_collection_from_ws

        #At some point might do deeper type checking...
        if not isinstance(mast_run_result_id, basestring):
            raise ValueError('Method find_sites_with_mast_by_collection_from_ws return value mast_run_result_id is not type basestring as required.')
        # return the results
        return [ mast_run_result_id ]
        
    def get_pspm_collection_from_meme_result(self, meme_run_result):
        # self.ctx is set by the wsgi application class
        # return variables are: meme_pspm_collection
        #BEGIN get_pspm_collection_from_meme_result
        #END get_pspm_collection_from_meme_result

        #At some point might do deeper type checking...
        if not isinstance(meme_pspm_collection, dict):
            raise ValueError('Method get_pspm_collection_from_meme_result return value meme_pspm_collection is not type dict as required.')
        # return the results
        return [ meme_pspm_collection ]
        
    def get_pspm_collection_from_meme_result_from_ws(self, ws_id, meme_run_result_id):
        # self.ctx is set by the wsgi application class
        # return variables are: meme_pspm_collection_id
        #BEGIN get_pspm_collection_from_meme_result_from_ws
        #END get_pspm_collection_from_meme_result_from_ws

        #At some point might do deeper type checking...
        if not isinstance(meme_pspm_collection_id, basestring):
            raise ValueError('Method get_pspm_collection_from_meme_result_from_ws return value meme_pspm_collection_id is not type basestring as required.')
        # return the results
        return [ meme_pspm_collection_id ]
        
